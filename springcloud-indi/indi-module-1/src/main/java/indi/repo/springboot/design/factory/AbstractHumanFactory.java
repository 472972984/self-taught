package indi.repo.springboot.design.factory;

/**
 * 功能说明:AbstractHumanFactory是一个抽象类，定义了一个创建人类具有的整体功能
 *
 * @author: ChenHQ
 * @date: 2021/8/9
 */
public abstract class AbstractHumanFactory {



    /**
     *     这里采用了泛型，对createHuman的输入参数产生两层限制
     *     1.必须是Class类型
     *     2.必须是Human的实现类
     */
    public abstract <T extends Human> T createHuman(Class<T> c);


}
