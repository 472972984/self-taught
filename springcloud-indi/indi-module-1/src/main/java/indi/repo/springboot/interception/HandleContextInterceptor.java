package indi.repo.springboot.interception;

import indi.repo.springboot.context.DefaultHandleContext;
import indi.repo.springboot.context.HandleContext;
import indi.repo.springboot.context.LocalHandleContext;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Objects;

import static indi.repo.springboot.common.constant.ApplicationConstant.*;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/6/19
 * @desc:
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
     * TODO: 模拟 解析请求
     *
     * @param request
     * @param response
     * @return
     */
    private HandleContext resolve(HttpServletRequest request, HttpServletResponse response) {
        String traceId = MDC.get(MDC_TRACE);
        DefaultHandleContext handleContext = new DefaultHandleContext();
        handleContext.setTraceId(traceId);
        handleContext.setOptDate(new Date().toString());

        //TODO: 模拟用户id
        String userId = request.getParameter("user");
        handleContext.setUserId(Objects.nonNull(userId) ? userId : null);
        return handleContext;
    }
}
