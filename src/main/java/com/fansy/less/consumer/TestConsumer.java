package com.fansy.less.consumer;

import com.fansy.less.constant.Constants;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author Fsy
 */
@Component
public class TestConsumer {

    @RabbitListener(queues = Constants.queue)
    @RabbitHandler
    public void consumer(Map obj, Message message, Channel channel) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            System.out.println(obj.get("id"));
            Thread.sleep(100);
            channel.basicAck(deliveryTag,true);
        } catch (Exception e) {
            channel.basicReject(deliveryTag,false);
            e.printStackTrace();
        }
    }
}
