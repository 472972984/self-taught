package indi.repo.springboot.jdk8.lock;

import cn.hutool.core.thread.ThreadUtil;

import java.util.concurrent.Semaphore;

/**
 * Semaphore：特点 —— 只能保证指定线程数执行，多余的线程等待其他线程释放
 * @author ChenHQ
 * @date 2022/10/27 16:38
 */
public class SemaphoreTest {

    public static void main(String[] args) throws Exception {

        Semaphore semaphore = new Semaphore(2);

        new Thread(() -> {
            try {
                semaphore.acquire();
                System.out.println("线程 AA 执行.....");
                ThreadUtil.safeSleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            semaphore.release();
        }).start();

        new Thread(() -> {
            try {
                semaphore.acquire();
                System.out.println("线程 BB 执行.....");
                ThreadUtil.safeSleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            semaphore.release();
        }).start();

        new Thread(() -> {
            try {
                semaphore.acquire();
                System.out.println("线程 CC 执行.....");
                ThreadUtil.safeSleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            semaphore.release();
        }).start();

        System.out.println("main 主线程执行完毕....");

    }

}
