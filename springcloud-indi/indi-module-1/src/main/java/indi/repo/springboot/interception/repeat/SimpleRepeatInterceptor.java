package indi.repo.springboot.interception.repeat;

import com.alibaba.fastjson.JSON;
import indi.repo.springboot.context.LocalHandleContext;
import indi.repo.springboot.interception.RepeatSubmitInterceptor;
import indi.repo.springboot.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author ChenHQ
 * @date: create in 2021/6/20
 */
@Component
@Slf4j
public class SimpleRepeatInterceptor extends RepeatSubmitInterceptor {

    private static final String separator = ":";

    private static final Integer expireTime = 30;

    @Override
    public Boolean isRepeatSubmit(HttpServletRequest request) {

        RedisTemplate<String, String> redisTemplate = SpringUtils.getBean("redisTemplate");

        String userId = LocalHandleContext.getHandleContext().getUserId();
        if (Objects.isNull(userId)) {
            return false;
        }

        String url = request.getRequestURI();
        String keyRedis = userId + separator + url;

        try {
            String oldParam = redisTemplate.opsForValue().get(keyRedis);
            String nowParam = JSON.toJSONString(request.getParameterMap());
            if (Objects.nonNull(oldParam)) {
                return oldParam.equals(nowParam);
            }

            redisTemplate.opsForValue().set(keyRedis, nowParam, expireTime, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("isRepeatSubmit error:{}", e);
            return false;
        }
        return false;
    }
}
