package indi.repo.springboot.design;

import java.util.Objects;

/**
 * 设计模式: 单例
 * 单例模式具备典型的3个特点：1、只有一个实例。 2、自我实例化。 3、提供全局访问点
 * @author: ChenHQ
 * @date: 2021/8/9
 * @desc:
 */
public class SingletonPattern {

    /**
     * 实例对象
     */
    private static volatile SingletonPattern instance = null;

    /**
     * 单例模式私有构造
     */
    private SingletonPattern() {
        System.out.println("SingletonPattern construct‘s method....");
    }

    /**
     * 获取单例实例
     *
     * @return 单例对象
     */
    public static SingletonPattern getInstance() {
        synchronized (SingletonPattern.class) {
            if (Objects.isNull(instance)) {
                instance = new SingletonPattern();
            }
        }
        return instance;
    }

    /**
     * 测试方法
     */
    public void testMethod() {
        System.out.println("invoke test method");
    }

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                SingletonPattern.getInstance().testMethod();
            }).start();
        }
    }


}
