package indi.repo.springboot.interception;

import indi.repo.springboot.common.annotation.RepeatSubmit;
import indi.repo.springboot.common.exception.BaseException;
import indi.repo.springboot.common.exception.enums.DemoExcepEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author ChenHQ
 * @date: create in 2021/6/20
 */
@Slf4j
public abstract class RepeatSubmitInterceptor extends HandlerInterceptorAdapter implements Ordered {

    /**
     * 默认执行顺序
     */
    private static final int DEFAULT_ORDER = HIGHEST_PRECEDENCE + 20;

    /**
     * POST 请求
     */
    private static final String namePost = "POST";

    /**
     * PUT 请求
     */
    private static final String namePut = "PUT";

    @Override
    public int getOrder() {
        return DEFAULT_ORDER;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            //1、被注解标识  2、 POST PUT 请求才做校验
            String methodReq = request.getMethod();

            HandlerMethod method = (HandlerMethod) handler;
            RepeatSubmit methodAnnotation = method.getMethodAnnotation(RepeatSubmit.class);

            if ((namePost.equalsIgnoreCase(methodReq) || namePut.equalsIgnoreCase(methodReq))
                    && Objects.nonNull(methodAnnotation)) {
                if (isRepeatSubmit(request)) {
                    throw new BaseException(DemoExcepEnum.REPEAT_SUBMIT);
                }
            } else {
                return true;
            }

            return true;
        } else {
            return super.preHandle(request, response, handler);
        }

    }

    /**
     * 是否重复提交
     *
     * @param request
     * @return
     */
    public abstract Boolean isRepeatSubmit(HttpServletRequest request);

}
