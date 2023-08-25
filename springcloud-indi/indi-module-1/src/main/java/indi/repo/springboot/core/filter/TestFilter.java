package indi.repo.springboot.core.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/6/19
 */
@Slf4j
public class TestFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("TestFilter coming here .... ");
        filterChain.doFilter(request, response);
    }
}
