package indi.repo.springboot.extend.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author ChenHQ
 * @date 2023/9/14 15:26
 */
@Component
public class BeanPostProcessorTest implements BeanPostProcessor {

    /**
     * BeanPostProcessor 的常用场景是针对在加载Bean前后需要进行的通用操作
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("testBeanServiceImpl")) {
            System.out.println("BeanPostProcessorTest.Before ===> " + beanName);
        }
        // step 1、可以封装AOP思想 —— 返回指定实例的代理对象（动态代理，包装 bean 实例以实现特定功能）
        // step 2、修改 bean 的属性值
        // step 3、在 bean 初始化之前和之后执行特定的逻辑，例如日志记录、性能监控等
        // step 4、在 bean 初始化之前和之后执行额外的数据处理
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("testBeanServiceImpl")) {
            System.out.println("BeanPostProcessorTest.After ===> " + beanName);
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
