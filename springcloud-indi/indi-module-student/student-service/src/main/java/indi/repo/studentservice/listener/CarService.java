package indi.repo.studentservice.listener;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author ChenHQ
 * @date 2022/3/22 14:01
 */
@Service
public class CarService {

    @Async
    @EventListener(OrderSuccessEvent.class)
    public void sendGoods() {
        System.out.println(Thread.currentThread().getName() + ": 发货.....");
    }


}
