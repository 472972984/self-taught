package indi.repo.springboot.core.filter;

import indi.repo.springboot.common.constant.ApplicationConstant;
import indi.repo.springboot.core.interception.CustomRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
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

    /**
     * 上传文件请求
     */
    private static final String MULTIPART_REQUEST = "multipart/";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        long start = System.currentTimeMillis();
        String traceId = StringUtils.remove(UUID.randomUUID().toString(), "-");
        MDC.put(ApplicationConstant.MDC_TRACE, traceId);
        try {
            //转换成代理类
            ServletRequest requestWrapper = null;

            if (StringUtils.startsWithIgnoreCase(request.getContentType(), MULTIPART_REQUEST)) {
                requestWrapper = new StandardMultipartHttpServletRequest(request);
            } else if (request instanceof HttpServletRequest) {
                requestWrapper = new CustomRequestWrapper((HttpServletRequest) request);
            }
            filterChain.doFilter(requestWrapper, response);
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
