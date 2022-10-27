package indi.repo.springboot.jdk8.lock;

import cn.hutool.core.thread.ThreadUtil;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock：多线程环境下只有获取到 锁资源 的线程方可执行
 * @author ChenHQ
 * @date 2022/10/27 16:43
 */
public class ReentryLockTest {

    public static void main(String[] args) {

        ReentrantLock lock = new ReentrantLock();

        new Thread(() -> {
            try {
                ThreadUtil.safeSleep(50);
                lock.lock();
                System.out.println("AAAA 执行 ...");
                ThreadUtil.safeSleep(1000);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }).start();

        new Thread(() -> {
            try {
                ThreadUtil.safeSleep(50);
                lock.lock();
                System.out.println("BBBB 执行 ...");
                ThreadUtil.safeSleep(1000);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }).start();

        new Thread(() -> {
            try {
                ThreadUtil.safeSleep(50);
                lock.lock();
                System.out.println("CCCC 执行 ...");
                ThreadUtil.safeSleep(1000);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }).start();

        System.out.println("main 方法线程执行完毕....");

    }
}
