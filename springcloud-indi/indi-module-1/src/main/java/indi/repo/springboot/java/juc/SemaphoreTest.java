package indi.repo.springboot.java.juc;

import java.util.concurrent.Semaphore;

/**
 * 模拟控制一个方法最大同时线程访问数
 *
 * @author ChenHQ
 * @date: create in 2021/10/17
 */
public class SemaphoreTest {

    private static Semaphore semaphore = new Semaphore(10);
/*
    public static void main(String[] args) {
//模拟100辆车进入停车场
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    try {
                        System.out.println("====" + Thread.currentThread().getName() + "来到停车场");
                        if (semaphore.availablePermits() == 0) {
                            System.out.println("车位不足，请耐心等待");
                        }
                        semaphore.acquire();//获取令牌尝试进入停车场
                        System.out.println(Thread.currentThread().getName() + "成功进入停车场");
                        Thread.sleep(new Random().nextInt(10000));//模拟车辆在停车场停留的时间
                        System.out.println(Thread.currentThread().getName() + "驶出停车场");
                        semaphore.release();//释放令牌，腾出停车场车位
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, i + "号车");
            thread.start();
        }
    }*/


    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    String name = Thread.currentThread().getName();
                    System.out.println(name + "当前线程尝试访问test方法");
                    if (semaphore.availablePermits() == 0) {
                        System.out.println(name + "当前访问线程已满，等待中");
                    }
                    semaphore.acquire();
                    test();
                    semaphore.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }


    }


    public static void test() throws Exception {
        String name = Thread.currentThread().getName();
        System.out.println(name + " do test method...");
        Thread.sleep(500); //模拟处理耗时
    }

}
