package indi.repo.springboot.java.juc;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ChenHQ
 * @date: create in 2021/11/9
 */
public class ThreadPoolTest {

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(50000));

    static volatile List<Integer> test = new LinkedList<>();

    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        for (int i = 0; i < 4; i++) {
            final Integer temp = (i + 1) * 25;
            final Integer tempI = i;

            executor.execute(() -> {
                int j = tempI * 25;
                while (j < temp) {
                    System.out.println("当前flag: " + j);
                    test.add(j);
                    j++;
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

        }

        executor.shutdown();
        while (!executor.isTerminated()) {
            // pass
        }

        long end = System.currentTimeMillis();
        System.out.println("执行完了 " + (end - start));

        System.out.println();

    }


}
