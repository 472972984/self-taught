package indi.repo.springboot.webconfig;

import indi.repo.springboot.filter.WebFilter;
import indi.repo.springboot.interception.HandleContextInterceptor;
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
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean<WebFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setName("WebFilter");
        filterRegistrationBean.setFilter(new WebFilter());
        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

}
