package indi.repo.openapi.factory;

import cn.hutool.extra.spring.SpringUtil;
import indi.repo.openapi.core.context.ApplicationStartupRunner;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author ChenHQ
 * @description: 客户端适配调用服务
 * @date 2021/12/21 14:08
 */
@Component
public class OpenClient {

    /**
     * 获取method对应service class类型
     * @param method
     * @return
     */
    public Class getServiceClassByMethod(String method) {
        Class aClass = ApplicationStartupRunner.getClassMap().get(method);
        if (Objects.isNull(aClass)) {
            throw new RuntimeException("请检查【" + method + "】是否有效");
        }
        return aClass;
    }


    /**
     * 获取 method 对应 service 实例
     * @param method    开放API对应method：【hde.order.item.get】
     * @return
     */
    public HdeOpenService getServiceByMethod(String method) {
        Class aClass = getServiceClassByMethod(method);
        return (HdeOpenService)SpringUtil.getBean(aClass);
    }

}
