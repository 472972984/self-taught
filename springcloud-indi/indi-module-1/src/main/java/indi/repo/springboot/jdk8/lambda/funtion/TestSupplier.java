package indi.repo.springboot.jdk8.lambda.funtion;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

/**
 * @author ChenHQ
 * @date 2023/4/26 18:36
 */
@Slf4j
public class TestSupplier {


    public static void main(String[] args) {
        String type = "xiaomi";
        String s = testSupplier(() -> {
            if ("xiaomi".equalsIgnoreCase(type)) {
                return "小米";
            } else {
                return "华为";
            }
        });
        System.out.println("s = " + s);

    }

    public static String testSupplier(Supplier<String> supplier) {
        System.out.println("使用供应方法");
        return supplier.get();
    }


}
