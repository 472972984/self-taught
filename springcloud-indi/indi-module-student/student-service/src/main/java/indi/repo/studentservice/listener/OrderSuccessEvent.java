package indi.repo.studentservice.listener;

import org.springframework.context.ApplicationEvent;

/**
 * @author ChenHQ
 * @description: TODO
 * @date 2022/3/22 13:52
 */
public class OrderSuccessEvent extends ApplicationEvent {

    public OrderSuccessEvent(Object source) {
        super(source);
    }

}

