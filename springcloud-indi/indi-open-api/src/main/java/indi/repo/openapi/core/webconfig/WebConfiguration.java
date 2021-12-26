package indi.repo.openapi.core.webconfig;

import indi.repo.openapi.core.filter.WebFilter;
import indi.repo.openapi.core.interception.HandleContextInterceptor;
import indi.repo.openapi.core.interception.PermitVerificationInterceptor;
import indi.repo.openapi.core.resolver.CustomResolver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date 2021/12/16
 * @desc:
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandleContextInterceptor());
        registry.addInterceptor(new PermitVerificationInterceptor());
    }

    /**
     * 添加自定义参数解析器
     * @param resolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new CustomResolver());
    }

    /**
     * 注入过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean<WebFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setName("WebFilter");
        filterRegistrationBean.setFilter(new WebFilter());
        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 10);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

}
