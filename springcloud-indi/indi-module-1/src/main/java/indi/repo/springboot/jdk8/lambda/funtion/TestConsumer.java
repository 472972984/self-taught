package indi.repo.springboot.jdk8.lambda.funtion;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

/**
 * @author ChenHQ
 * @date 2023/4/26 18:36
 */
@Slf4j
public class TestConsumer {


    public static void main(String[] args) {
        testConsumer(System.out::println, "1");
    }

    /**
     * @param consumer 输出长度消费方式
     */
    public static void testConsumer(Consumer<String> consumer, String param) {
        System.out.println("使用消费方法：" + param);
        consumer.accept(param);
    }


}
