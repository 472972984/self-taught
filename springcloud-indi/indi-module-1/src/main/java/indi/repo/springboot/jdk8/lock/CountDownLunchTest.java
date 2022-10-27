package indi.repo.springboot.jdk8.lock;

import cn.hutool.core.thread.ThreadUtil;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch: 特点 —— 必须等到 计数器到达0时，主线程方可继续执行
 * @author ChenHQ
 * @date 2022/10/27 16:53
 */
public class CountDownLunchTest {

    public static void main(String[] args) throws Exception {

        CountDownLatch countDownLatch = new CountDownLatch(3);

        new Thread(() -> {
            try {
                System.out.println("AAAA 执行 ...");
                ThreadUtil.safeSleep(1000);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                countDownLatch.countDown();
            }
        }).start();

        new Thread(() -> {
            try {
                System.out.println("BBBB 执行 ...");
                ThreadUtil.safeSleep(1000);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                countDownLatch.countDown();
            }
        }).start();

        new Thread(() -> {
            try {
                System.out.println("CCCC 执行 ...");
                ThreadUtil.safeSleep(1000);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                countDownLatch.countDown();
            }
        }).start();

        countDownLatch.await();
        System.out.println("main 方法线程执行完毕....");


    }

}
