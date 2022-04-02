package indi.repo.openapi.core.interception;

import cn.hutool.core.date.DateUtil;
import indi.repo.openapi.constant.ApplicationConstant;
import indi.repo.openapi.core.context.DefaultHandleContext;
import indi.repo.openapi.core.context.HandleContext;
import indi.repo.openapi.core.context.LocalHandleContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;


/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date 2021/12/16 16:35
 */
@Slf4j
public class HandleContextInterceptor extends HandlerInterceptorAdapter implements Ordered {

    /**
     * 默认执行顺序
     */
    private static final int DEFAULT_ORDER = HIGHEST_PRECEDENCE + 10;


    @Override
    public int getOrder() {
        return DEFAULT_ORDER;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (Objects.isNull(LocalHandleContext.getHandleContext())) {
            // 生成上下文
            HandleContext context = resolve(request, response);
            LocalHandleContext.bindHandleContext(context);
        }
        return true;
    }

    /**
     * 解析请求
     * @param request
     * @param response
     * @return
     */
    private HandleContext resolve(HttpServletRequest request, HttpServletResponse response) {
        DefaultHandleContext handleContext = new DefaultHandleContext();

        //上下文中唯一请求id
        String traceId = MDC.get(ApplicationConstant.MDC_TRACE);
        handleContext.setTraceId(traceId);

        //TODO: 这些公共参数需要规定放在哪里, 建议放在请求头中

        //获取请求参数中 app_key
        String appKey = request.getHeader(ApplicationConstant.APP_KEY);
        if (StringUtils.isBlank(appKey)) {
            throw new RuntimeException("请求头中未检测到【app-key】值");
        }
        handleContext.setAppKey(appKey);

        //获取请求参数中 version
        String version = request.getHeader(ApplicationConstant.VERSION);
        if (StringUtils.isBlank(version)) {
            throw new RuntimeException("请求头中未检测到【version】值");
        }
        handleContext.setVersion(version);

        //获取请求参数中 sign_method
        String signMethod = request.getHeader(ApplicationConstant.SIGN_METHOD);
        if (StringUtils.isBlank(signMethod)) {
            throw new RuntimeException("请求头中未检测到【sign-method】值");
        }
        handleContext.setSignMethod(signMethod);

        //获取请求参数中 timestamp
        String timestamp = request.getHeader(ApplicationConstant.TIMESTAMP);
        if (StringUtils.isBlank(timestamp)) {
            throw new RuntimeException("请求头中未检测到【timestamp】值");
        }
        try {
            DateUtil.parse(timestamp, ApplicationConstant.DATE_YYYYMMDDHHMMSS);
        } catch (Exception e) {
            throw new RuntimeException("【timestamp】格式不正确[yyyy-MM-dd HH:mm:ss]");
        }
        handleContext.setTimestamp(timestamp);

        //获取请求参数中 sign
        String sign = request.getHeader(ApplicationConstant.SIGN);
        if (StringUtils.isBlank(sign)) {
            throw new RuntimeException("请求头中未检测到【sign】值");
        }
        handleContext.setSign(sign);

        //获取请求参数中 method
        String method = request.getHeader(ApplicationConstant.METHOD);
        if (StringUtils.isBlank(method)) {
            throw new RuntimeException("请求头中未检测到【method】值");
        }
        handleContext.setMethod(method);

        //获取请求参数中 randomKey
        String randomKey = request.getHeader(ApplicationConstant.RANDOM_KEY);
        handleContext.setRandomKey(randomKey);

        //获取请求参数中 encryptParm
        String encryptParm = request.getHeader(ApplicationConstant.ENCRYPT_PARM);
        handleContext.setEncryptParm(encryptParm);

        return handleContext;
    }

    /**
     * 移除上下文信息
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        super.afterCompletion(request, response, handler, ex);
        LocalHandleContext.removeHandleContext();
    }
}
