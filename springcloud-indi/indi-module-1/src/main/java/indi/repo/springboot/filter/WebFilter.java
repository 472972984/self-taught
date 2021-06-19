package indi.repo.springboot.filter;

import indi.repo.springboot.common.constant.ApplicationConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/6/19
 * @desc:
 */
@Slf4j
public class WebFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        long start = System.currentTimeMillis();
        String traceId = StringUtils.remove(UUID.randomUUID().toString(), "-");
        log.info("Current traceId is {}.", traceId);
        MDC.put(ApplicationConstant.MDC_TRACE, traceId);
        try {
            //转换成代理类
            filterChain.doFilter(request, response);
        } catch (Exception cause) {
            log.error("Filter error:{}", cause.getMessage());
            // 全局异常处理-只处理json情况
        } finally {
            MDC.remove(ApplicationConstant.MDC_TRACE);
            long end = System.currentTimeMillis();
            log.info("Request cost time:{} ms", end - start);
        }
    }
}
