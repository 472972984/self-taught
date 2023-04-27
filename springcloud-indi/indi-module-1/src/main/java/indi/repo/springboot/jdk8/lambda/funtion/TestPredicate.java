package indi.repo.springboot.jdk8.lambda.funtion;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Predicate;

/**
 * @author ChenHQ
 * @date 2023/4/26 18:36
 */
@Slf4j
public class TestPredicate {


    public static void main(String[] args) {

        System.out.println(testPredicate(param -> param > 2, 1));

    }

    public static boolean testPredicate(Predicate<Integer> predicate, Integer param) {
        System.out.println("使用断言方法：" + param);
        // param 相当于
        return predicate.test(param);
    }


}
