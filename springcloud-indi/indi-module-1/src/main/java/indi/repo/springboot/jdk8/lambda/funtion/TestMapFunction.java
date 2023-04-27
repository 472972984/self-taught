package indi.repo.springboot.jdk8.lambda.funtion;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author ChenHQ
 * @date 2023/4/26 16:56
 */
@Slf4j
public class TestMapFunction {

    /**
     * 业务逻辑分派Map
     * Function为函数式接口，
     * 下面代码中 Function<String, Object> 的含义是接收一个String类型的变量用来获取你要执行哪个Function，实际使用中可自行定义
     * Function执行完成返回一个Object类型的结果,这个结果就是统一的业务处理返回结果，实际使用中可自行定义
     */
    private static final Map<String, Function<String, String>> checkResultDispatcher = new HashMap<>();

    static {
        checkResultDispatcher.put("xiaomi", TestMapFunction::buildXiaomi);
        checkResultDispatcher.put("huawei", TestMapFunction::buildHuawei);
        checkResultDispatcher.put("vivo", TestMapFunction::buildVivo);
    }

    public static void main(String[] args) {

        String phoneType = "xiaomi";
        String param = "黑色";
        Function<String, String> result = checkResultDispatcher.get(phoneType);
        String apply;
        if (result != null) {
            //执行这段表达式获得String类型的结果
            apply = result.apply(param);
            System.out.println("apply = " + apply);
        } else {
            log.error("出错了～");
        }
    }

    public static String buildXiaomi(String param) {
        log.info("创造小米手机:{}", param);
        return "小米手机颜色参数：" + param;
    }

    public static String buildHuawei(String param) {
        log.info("创造华为手机:{}", param);
        return "华为手机颜色参数：" + param;
    }

    public static String buildVivo(String param) {
        log.info("创造vivo手机:{}", param);
        return "vivo手机颜色参数：" + param;
    }


}
