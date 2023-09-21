package indi.repo.springboot.extend.aware;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author ChenHQ
 * @date 2023/9/14 15:15
 */
@Component
public class EnvironmentAwareTest implements EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public void test() {
        String[] profiles = environment.getDefaultProfiles();
        System.out.println(profiles);
    }

}
