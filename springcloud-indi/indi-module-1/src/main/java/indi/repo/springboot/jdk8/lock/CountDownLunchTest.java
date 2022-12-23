package indi.repo.springboot.jdk8.lock;

/**
 * CountDownLatch: 特点 —— 必须等到 计数器到达0时，主线程方可继续执行
 *
 * @author ChenHQ
 * @date 2022/10/27 16:53
 */
public class CountDownLunchTest {

    public static void main(String[] args) {

        try {
            System.out.println("123");
            throw new RuntimeException("ex....");
        } catch (Exception e) {
            System.out.println("捕获异常 ....");
        } finally {
            System.out.println("执行 finally");
        }


    }
}
