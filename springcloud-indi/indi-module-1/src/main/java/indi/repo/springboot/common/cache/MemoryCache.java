package indi.repo.springboot.common.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.collect.Maps;
import indi.repo.common.utils.ReflectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

/**
 * 内存缓存
 * @author
 */
@Component
@Slf4j
public class MemoryCache {

    private static Cache<String, Object> caffeineCache = Caffeine.newBuilder()
            // 设置最后一次写入经过60s过期
            .expireAfterWrite(60, TimeUnit.SECONDS)
            // 初始的缓存空间大小
            .initialCapacity(100)
            // 缓存的最大条数
            .maximumSize(1000)
            .build();

    private static Cache<String, ReentrantLock> lockCache = Caffeine.newBuilder()
            // 设置最后一次写入经过1小时
            .expireAfterWrite(1, TimeUnit.HOURS)
            // 初始的缓存空间大小
            .initialCapacity(100)
            // 缓存的最大条数
            .maximumSize(1000)
            .build();

    private static Map<String, ReentrantLock> lockMap = Maps.newConcurrentMap();

    public static Object getCache(String cacheName, List<Object> pars, Function<Object, Object> function) {
        String key = generateKey(cacheName, pars);
        return caffeineCache.get(key, s -> {
            ReentrantLock lock = lockCache.get(key, o -> new ReentrantLock());
            // not null
            lock.lock();
            try {
                log.info("lock by key: {}, lock: {}", key, lock.hashCode());
                return function.apply(pars);
            } finally {
                log.info("unlock lock by key: {}", key);
                lock.unlock();
            }
        });
    }

    private static String generateKey(String cacheName, List<Object> pars) {
        CacheKey cacheKey = new CacheKey();
        cacheKey.update(cacheName);
        if (CollectionUtils.isEmpty(pars)) {
            cacheKey.update("null");
        } else {
            pars.forEach(par -> {
                List<Field> fields = ReflectUtils.getAllFields(par);
                fields.forEach(field -> {
                    field.setAccessible(true);
                    String fileName = field.getName();
                    Object value = ReflectionUtils.getField(field, par);
                    cacheKey.update(fileName);
                    cacheKey.update(value == null ? "null" : value);
                });
            });
        }
        return cacheName + ":" + cacheKey.hashCode();
    }
}
