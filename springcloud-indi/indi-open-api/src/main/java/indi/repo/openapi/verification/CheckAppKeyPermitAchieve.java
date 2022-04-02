package indi.repo.openapi.verification;

import indi.repo.openapi.core.context.DefaultHandleContext;
import indi.repo.openapi.core.context.HandleContext;
import indi.repo.openapi.core.context.LocalHandleContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * @author ChenHQ
 * 校验 appKey 核验
 * @date 2021/12/17 14:01
 */
@Repository
@Slf4j
public class CheckAppKeyPermitAchieve implements CheckAppKeyPermit {

    @Value("${hde.appkey:1474c0b6ca00299602f64}")
    private String hdeAppkey;

    @Value("${hde.secret:TEBLPplIBm_PNR7uPLnR}")
    private String secret;

    /**
     * 校验 appKey 是否合法
     *
     */
    @Override
    public boolean checkPermit(String appKey) {
        HandleContext handleContext = LocalHandleContext.getHandleContext();
        if (org.apache.commons.lang3.StringUtils.equals(appKey, hdeAppkey)) {
            ((DefaultHandleContext) handleContext).setSecret(secret);
            return true;
        }
        log.error("【check app-key failed - {}】", appKey);
        return false;
    }
}
