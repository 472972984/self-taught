package indi.repo.springboot.extend.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * @author ChenHQ
 * @date 2023/9/14 15:23
 */
//@Component
public class BeanFactoryPostProcessorTest implements BeanFactoryPostProcessor {

    /**
     * BeanFactoryPostProcessor和BeanPostProcessor都是针对容器内全部Bean实例进行自定义操作的，
     * BeanFactoryPostProcessor可以获取BeanFactory，所以能够对bean的定义进行操作。
     * BeanPostProcessor获取的是要加载到容器中的bean和beanName，其处理顺序在BeanFactoryPostProcessor之后。
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {

    }
}
