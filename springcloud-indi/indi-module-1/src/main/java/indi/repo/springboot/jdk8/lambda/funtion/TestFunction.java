package indi.repo.springboot.jdk8.lambda.funtion;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

/**
 * @author ChenHQ
 * @date 2023/4/26 18:36
 */
@Slf4j
public class TestFunction {


    public static void main(String[] args) {

        String s = testFunction(param -> {
            System.out.println("执行 function ： " + param);
            return "chenhuiqi" + param;
        }, 25);

        System.out.println(s);


    }

    public static String testFunction(Function<Integer, String> function, Integer param) {
        return function.apply(param);
    }


}
