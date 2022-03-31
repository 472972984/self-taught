package indi.repo.studentservice.listener.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @author ChenHQ
 * @description: TODO
 * @date 2022/3/28 16:47
 */
@Service
@Slf4j
public class MqListenner {

    @Service
    @RocketMQMessageListener(consumerGroup = "CONSUMER_GROUP_3", topic = "TEST_TOPIC")
    public class Consumer3 implements RocketMQListener<String> {
        @Override
        public void onMessage(String s) {
            log.info("consumer3 rocket收到消息：{}", s);
        }
    }

    @Service
    @RocketMQMessageListener(consumerGroup = "CONSUMER_GROUP_1", topic = "TEST_TOPIC")
    public class Consumer1 implements RocketMQListener<String> {
        @Override
        public void onMessage(String s) {
            log.info("consumer1 rocket收到消息：{}", s);
        }
    }

    @Service
    @RocketMQMessageListener(consumerGroup = "CONSUMER_GROUP_2", topic = "TEST_TOPIC")
    public class Consumer2 implements RocketMQListener<String> {
        @Override
        public void onMessage(String s) {
            log.info("consumer2 rocket收到消息：{}", s);
        }
    }
}