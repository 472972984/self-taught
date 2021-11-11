package indi.repo.springboot.java.juc;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ChenHQ 多个任务合并（模拟压测并发提交）
 * @date: create in 2021/10/17
 */
public class CycleBarrierTest {
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Runnable() {
        // 当计数器为0时，立即执行
        @Override
        public void run() {
            System.out.println("汇总线程：" + Thread.currentThread().getName() + " 任务合并。");
        }
    });

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // 将线程A添加到线程池
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("线程A：" + Thread.currentThread().getName() + "执行任务。");
                    System.out.println("线程A：到达屏障点");
                    cyclicBarrier.await();
                    System.out.println("线程A：退出屏障点");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // 将线程B添加到线程池
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("线程B：" + Thread.currentThread().getName() + "执行任务。");
                    System.out.println("线程B：到达屏障点");
                    cyclicBarrier.await();
                    System.out.println("线程B：退出屏障点");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // 关闭线程池
        executorService.shutdown();
    }
}

