package indi.repo.openapi.verification;

import indi.repo.openapi.core.context.DefaultHandleContext;
import indi.repo.openapi.core.context.HandleContext;
import indi.repo.openapi.core.context.LocalHandleContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * @author ChenHQ
 * @description: 校验 appKey 核验
 * @date 2021/12/17 14:01
 */
@Repository
@Slf4j
public class CheckAppKeyPermitAchieve implements CheckAppKeyPermit {

    @Value("${dev.appkey:dev123456}")
    private String devAppKey;

    @Value("${dev.secret:dev789}")
    private String devSecret;

    @Value("${test.appkey:test123456}")
    private String testAppKey;

    @Value("${test.secret:test789}")
    private String testSecret;

    /**
     * 校验 appKey 是否合法
     * @param appKey
     * @return
     */
    @Override
    public boolean checkPermit(String appKey) {
        HandleContext handleContext = LocalHandleContext.getHandleContext();
        if(org.apache.commons.lang3.StringUtils.equals(appKey,devAppKey)) {
            ((DefaultHandleContext)handleContext).setSecret(devSecret);
            return true;
        }else if(org.apache.commons.lang3.StringUtils.equals(appKey,testAppKey)) {
            ((DefaultHandleContext)handleContext).setSecret(testSecret);
            return true;
        }
        log.error("【check app-key failed - {}】",appKey);
        return false;
    }
}
