package indi.repo.springboot.extend.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author ChenHQ
 * @date 2023/9/14 14:43
 */
@Component
public class ApplicationCommandRunner implements CommandLineRunner {

    /**
     * 应用启动之后，做一些初始化动作
     */
    @Override
    public void run(String... args) throws Exception {
        System.out.println(">>>>>>>>>>>>>>>>>>>开始加载缓存服务>>>>>>>>>>>>>>>>>>>");
    }
}
