package indi.repo.openapi.core.context;

import cn.hutool.core.collection.CollectionUtil;
import indi.repo.openapi.annotation.OpenApiEvent;
import indi.repo.openapi.annotation.OpenApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * @author ChenHQ
 * @description: 容器初始化加载必要参数信息
 * @date 2021/12/21 11:18
 */
@Component
public class ApplicationStartupRunner implements ApplicationRunner {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 存放【method】映射关系
     */
    private static Map<String,Class> classMap = new ConcurrentHashMap<>();

    /**
     * 存放【method】param映射关系
     */
    private static Map<String,Class> classParamMap = new ConcurrentHashMap<>();

    /**
     * 存放 rpc【method】param映射关系
     */
    private static Map<String,Class> classRpcParamMap = new ConcurrentHashMap<>();

    /**
     * 扫描项目自定义注解装配
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) {
        openApiEventScan();
        openApiParamScan();
    }

    /**
     * 扫描自定义注解
     */
    private void openApiEventScan() {
        Map<String, Object> annotation = applicationContext.getBeansWithAnnotation(OpenApiEvent.class);

        if (CollectionUtil.isNotEmpty(annotation)) {
            annotation.forEach((s, o) -> {
                Class<?> aClass = o.getClass();
                OpenApiEvent openApiEvent = aClass.getAnnotation(OpenApiEvent.class);
                String[] value = openApiEvent.value();
                //不为空
                Assert.notEmpty(value);
                Stream.of(value).forEach(methodKey -> {
                    if (Objects.nonNull(classMap.get(methodKey))) {
                        throw new RuntimeException("存在重复的【" + methodKey + "】 method 接口定义");
                    }
                    classMap.put(methodKey, aClass);
                });
            });
        }
    }


    /**
     * 扫描自定义注解
     */
    private void openApiParamScan() {
        Map<String, Object> annotation = applicationContext.getBeansWithAnnotation(OpenApiParam.class);

        if (CollectionUtil.isNotEmpty(annotation)) {
            annotation.forEach((s, o) -> {
                Class<?> aClass = o.getClass();
                OpenApiParam apiParam = aClass.getAnnotation(OpenApiParam.class);
                String value = apiParam.value();
                Class paramRpcClass = apiParam.paramRpcClass();
                //不为空
                Assert.notNull(value);
                Assert.notNull(paramRpcClass);
                Stream.of(value).forEach(methodKey -> {

                    if (Objects.nonNull(classParamMap.get(methodKey))) {
                        throw new RuntimeException("存在重复的【" + methodKey + "】 参数定义");
                    }
                    classParamMap.put(methodKey, aClass);

                    if (Objects.nonNull(classRpcParamMap.get(methodKey))) {
                        throw new RuntimeException("存在重复的【" + methodKey + "】 参数定义");
                    }
                    classRpcParamMap.put(methodKey, paramRpcClass);
                });

            });
        }
    }

    /**
     * 获取【method】映射map
     * @return
     */
    public static Map<String, Class> getClassMap() {
        return classMap;
    }

    /**
     * 获取【method】param映射关系map
     * @return
     */
    public static Map<String, Class> getClassParamMap() {
        return classParamMap;
    }

    /**
     * 获取rpc【method】param映射关系map
     * @return
     */
    public static Map<String, Class> getClassRpcParamMap() {
        return classRpcParamMap;
    }
}
