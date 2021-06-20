package indi.repo.springboot.common.handler;

import indi.repo.springboot.common.Result;
import indi.repo.springboot.common.constant.ApplicationConstant;
import indi.repo.springboot.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/6/18
 * @desc:
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result handleException(HttpServletRequest request, Exception e) {
        String url = request.getRequestURI();
        String errorMsg;
        if (e instanceof BaseException) {
            BaseException exp = (BaseException) e;
            log.debug("error StackTrace,{}", e);
            log.info("business error: code:{},msg:{}", exp.getErrorCode(), exp.getMessage());
            errorMsg = exp.getMessage();
            return Result.fail(exp.getErrorCode(), errorMsg);
        } else {
            log.error("system error:traceId:{}, url:{},\r\n error StackTrace:{}", MDC.get(ApplicationConstant.MDC_TRACE), url, e);
            return Result.fail("系统异常，请联系管理员");
        }

    }


}
