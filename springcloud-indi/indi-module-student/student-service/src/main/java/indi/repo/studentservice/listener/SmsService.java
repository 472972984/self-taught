package indi.repo.studentservice.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 * @author ChenHQ
 * @description: TODO
 * @date 2022/3/22 13:54
 */
@Service
public class SmsService implements ApplicationListener<OrderSuccessEvent> {

    @Override
    public void onApplicationEvent(OrderSuccessEvent orderSuccessEvent) {
        this.sendSms("15797679578","123456");
    }

    public void sendSms(String phone, String code) {
        System.out.println("发送手机号" + phone + "\t code:" + code);
    }

}
