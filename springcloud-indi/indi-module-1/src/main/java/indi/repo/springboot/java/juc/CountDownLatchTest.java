package indi.repo.springboot.java.juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 测试CountDownLatch  主线程希望等待子线程全部执行完再结束
 *
 * @author ChenHQ
 * @date: create in 2021/10/17
 */
public class CountDownLatchTest {

    public static void main(String[] args) throws Exception {
        CountDownLatch latch = new CountDownLatch(10);

        System.out.println("主线程开始任务....");

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                System.out.println("子线程：" + Thread.currentThread().getName() + "执行任务");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
            }, String.valueOf(i)).start();
        }

        System.out.println("等待子线程运行结束");
        latch.await(10, TimeUnit.SECONDS);
        System.out.println("子线程运行结束");
        System.out.println("主线程完成任务....");

    }


}
