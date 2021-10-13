package indi.repo.springboot.java.thread;

/**
 * @author ChenHQ
 * @date: create in 2021/10/13
 */
public class MyThread extends Thread {

    @Override
    public void run() {
        Thread thread = Thread.currentThread();
        System.out.println("my thread start ..." + thread.getName());
    }

}