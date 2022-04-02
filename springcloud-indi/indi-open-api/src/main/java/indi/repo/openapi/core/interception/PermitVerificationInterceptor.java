package indi.repo.openapi.core.interception;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.google.common.collect.Maps;
import indi.repo.openapi.constant.ApplicationConstant;
import indi.repo.openapi.core.context.HandleContext;
import indi.repo.openapi.core.context.LocalHandleContext;
import indi.repo.openapi.util.SignEncodeUtils;
import indi.repo.openapi.verification.CheckAppKeyPermit;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static indi.repo.openapi.constant.ApplicationConstant.THREAD_LOCAL_SIGN_PARAM;

/**
 * @author ChenHQ
 * 验证许可拦截器
 * @date 2021/12/16 16:35
 */
@Slf4j
public class PermitVerificationInterceptor extends HandlerInterceptorAdapter implements Ordered {

    /**
     * 时间戳最大误差10分钟
     */
    private static final int MAX_DEVIATION = 10 * 60 * 1000;

    /**
     * 默认执行顺序: 在解析上下文之后执行
     */
    private static final int DEFAULT_ORDER = HIGHEST_PRECEDENCE + 5;

    @Override
    public int getOrder() {
        return DEFAULT_ORDER;
    }

    /**
     * 接口api调用许可认证
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //获取上下文信息
        HandleContext handleContext = LocalHandleContext.getHandleContext();

        //第一步校验：请求时间戳是否合法
        String timestamp = handleContext.getTimestamp();
        long time = DateUtil.parse(timestamp).getTime();
        long current = DateUtil.current();
        if (Math.abs(current - time) > MAX_DEVIATION) {
            throw new RuntimeException("服务端允许客户端请求最大时间误差为10分钟,当前服务器时间【" + DateUtil.now() + "】");
        }

        //第二步校验：调用RPC获取AppKey是否存在，并且得到对应的secret
        CheckAppKeyPermit checkAppKeyPermit = SpringUtil.getBean(CheckAppKeyPermit.class);
        String appKey = handleContext.getAppKey();
        boolean flag = checkAppKeyPermit.checkPermit(appKey);
        if (!flag) {
            throw new RuntimeException("app_key无效");
        }

        //第三步校验：上送的sign值是否和服务端计算的一致
        Map<String, String> paramsMap = paramsSign(request);
        //服务端计算的sign值
        String signServer = SignEncodeUtils.signTopRequest(paramsMap, handleContext.getSecret(), handleContext.getSignMethod());

        //服务端计算sign值和上送的sign值做一致性校验
        try {
            if (signServer.equalsIgnoreCase(handleContext.getSign())) {
                //TODO：如果校验通过，并且传入了 对称加密参数——进行参数解密

                //填充入参类型
                //setParamClass();
                log.info("[请求正常通过...]");
                return true;
            } else {
                throw new RuntimeException("sign值校验失败：computeSignature is:【" + MDC.get(THREAD_LOCAL_SIGN_PARAM) + "】");
            }
        } finally {
            try {
                MDC.remove(THREAD_LOCAL_SIGN_PARAM);
            } catch (IllegalArgumentException e) {
                log.error("remove computeSignature error:{}", e.getMessage());
            }
        }

    }


    /**
     * 对所有API请求参数（包括公共参数和业务参数，但除去sign参数和byte[]类型的参数）构建map集合
     *
     * @param request
     * @return
     */
    private Map<String, String> paramsSign(HttpServletRequest request) {
        //返回对象
        Map<String, String> result = Maps.newConcurrentMap();

        //获取上下文信息
        HandleContext handleContext = LocalHandleContext.getHandleContext();

        //map中存放公共参数
        result.put(ApplicationConstant.APP_KEY, handleContext.getAppKey());
        result.put(ApplicationConstant.VERSION, handleContext.getVersion());
        result.put(ApplicationConstant.SIGN_METHOD, handleContext.getSignMethod());
        result.put(ApplicationConstant.TIMESTAMP, handleContext.getTimestamp());
        result.put(ApplicationConstant.METHOD, handleContext.getMethod());


        String randomKey = handleContext.getRandomKey();
        if (StringUtils.hasText(randomKey)) {
            result.put(ApplicationConstant.RANDOM_KEY, randomKey);
        }

        String encryptParm = handleContext.getEncryptParm();
        if (StringUtils.hasText(encryptParm)) {
            result.put(ApplicationConstant.ENCRYPT_PARM, encryptParm);
        }

        //业务参数: TODO：怎么做到统一: 编写对外文档的时候注明
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (CollectionUtil.isNotEmpty(parameterMap)) {
            parameterMap.forEach((key, strings) -> {
                result.put(key, Stream.of(strings).collect(Collectors.joining(",")));
            });
        }

        return result;
    }


}
