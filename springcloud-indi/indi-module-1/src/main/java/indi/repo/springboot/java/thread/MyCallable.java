package indi.repo.springboot.java.thread;

import java.util.concurrent.Callable;

/**
 * @author ChenHQ
 * @date: create in 2021/10/13
 */
public class MyCallable implements Callable<Boolean> {
    @Override
    public Boolean call() throws Exception {
        System.out.println("my callable start ...");
        return true;
    }
}
