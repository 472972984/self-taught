package indi.repo.springboot.test.engine;

import indi.repo.springboot.test.engine.handle.AbstractHandler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author ChenHQ
 * @date 2025/4/8 21:52
 */
@Component
public class HandlerFactory implements ApplicationContextAware {

    private ApplicationContext context;

    public AbstractHandler getHandler(PluginDTO pluginDTO) {
        AbstractHandler.HandlerBuilder builder = new AbstractHandler.HandlerBuilder();
        context.getBeansOfType(AbstractHandler.class).values().stream().sorted().forEach(abstractHandler -> {
            abstractHandler.setPluginDTO(pluginDTO);
            builder.addLastHandler(abstractHandler);
        });
        return builder.build();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
