package indi.repo.springboot.core.webconfig;

import indi.repo.springboot.core.filter.RemoveFilter;
import indi.repo.springboot.core.filter.TestFilter;
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

    @Bean
    public FilterRegistrationBean filterRegistrationBeanTest() {
        FilterRegistrationBean<TestFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setName("TestFilter");
        filterRegistrationBean.setFilter(new TestFilter());
        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 9);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBeanTest2() {
        FilterRegistrationBean<RemoveFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setName("RemoveFilter");
        filterRegistrationBean.setFilter(new RemoveFilter());
        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 8);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

}
