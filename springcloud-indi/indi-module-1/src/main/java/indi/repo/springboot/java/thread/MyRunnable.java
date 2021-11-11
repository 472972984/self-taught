package indi.repo.springboot.java.thread;

/**
 * @author ChenHQ
 * @date: create in 2021/10/13
 */
public class MyRunnable implements Runnable{

    @Override
    public void run() {
        Thread thread = Thread.currentThread();
        System.out.println("My runnable start ..." + thread.getName());
    }
}
