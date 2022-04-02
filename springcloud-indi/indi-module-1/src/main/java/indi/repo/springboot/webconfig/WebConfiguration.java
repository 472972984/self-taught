package indi.repo.springboot.webconfig;

import indi.repo.springboot.core.filter.WebFilter;
import indi.repo.springboot.core.interception.HandleContextInterceptor;
import indi.repo.springboot.core.interception.repeat.SimpleRepeatInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 功能说明:
 *
 * @author: ChenHQ
 * @date: 2021/6/19
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
        registry.addInterceptor(new SimpleRepeatInterceptor());
    }

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
