package indi.repo.openapi.verification;

import indi.repo.openapi.core.context.DefaultHandleContext;
import indi.repo.openapi.core.context.HandleContext;
import indi.repo.openapi.core.context.LocalHandleContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Objects;

/**
 * @author ChenHQ
 * @description: 校验 appKey 核验
 * @date 2021/12/17 14:01
 */
@Repository
@Slf4j
public class CheckAppKeyPermitAchieve implements CheckAppKeyPermit {

    /**
     * 校验 appKey 是否合法
     * @param appKey
     * @return
     */
    @Override
    public boolean checkPermit(String appKey) {
        //TODO：开放一个微服务调用接口，获取 appKey 是否合法，及对应的 secret
        // mock 一个假的
        String secret = "helloWorld";
        HandleContext handleContext = LocalHandleContext.getHandleContext();
        if(Objects.nonNull(handleContext)) {
            //将拿到的 secret 放入上下文中
            ((DefaultHandleContext)handleContext).setSecret(secret);
            return true;
        }
        log.error("【check app-key failed - {}】",appKey);
        return false;
    }
}
