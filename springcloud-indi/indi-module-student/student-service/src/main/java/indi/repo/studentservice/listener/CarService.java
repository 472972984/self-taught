package indi.repo.studentservice.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * @author ChenHQ
 * @description: TODO
 * @date 2022/3/22 14:01
 */
@Service
public class CarService {

    @EventListener(OrderSuccessEvent.class)
    public void sendGoods() {
        System.out.println("发货.....");
    }


}
