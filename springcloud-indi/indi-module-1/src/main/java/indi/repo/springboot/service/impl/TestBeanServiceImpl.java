package indi.repo.springboot.service.impl;

import indi.repo.springboot.service.TestBeanService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author ChenHQ
 * @date 2023/9/14 16:12
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TestBeanServiceImpl implements TestBeanService, ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("TestBeanServiceImpl setApplicationContext ");
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("TestBeanServiceImpl  afterPropertiesSet ");
    }

    public TestBeanServiceImpl() {
        System.out.println("TestBeanServiceImpl construct");
    }

    @PostConstruct
    public void init() {
        System.out.println("TestBeanServiceImpl init");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("TestBeanServiceImpl destroy");
    }

    @Override
    public void test() {
        System.out.println("TestBeanServiceImpl test method");
    }
}


