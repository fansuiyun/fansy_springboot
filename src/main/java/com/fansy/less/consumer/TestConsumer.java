package com.fansy.less.consumer;

import com.fansy.less.constant.Constants;
import com.fansy.less.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Fsy
 */
@Slf4j
@Component
public class TestConsumer {

    private ObjectMapper mapper=new ObjectMapper();

    int count = 0;

    @RabbitListener(queues = Constants.queue)
    @RabbitHandler
    public void consumer(List<Message> messageList, Channel channel) throws IOException {
        count++;
        long deliveryTag=0;
        List<User> userList = new ArrayList<>();
        for (Message message : messageList) {
            deliveryTag = message.getMessageProperties().getDeliveryTag();
            User user = mapper.readValue(message.getBody(), User.class);
            try {
                if(deliveryTag%2==0) {
                    userList.add(user);
                }else{
                    log.error("========="+user.toString()+"=========");
                    channel.basicReject(deliveryTag,false);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(count);
        userList.forEach(System.out::println);
        channel.basicAck(deliveryTag,true);


        /*List<User> esLogMatchDataList = new ArrayList<>(messageList.size());
        long deliveryTag = 0;
        User user = null;
        for (Message message : messageList) {
            deliveryTag = message.getMessageProperties().getDeliveryTag();
            try {
                user = mapper.readValue(message.getBody(), User.class);
                esLogMatchDataList.add(user);
                log.info("receive change message. content={}", user.toString());
            } catch (Exception e) {
                log.error("change message 转换类型出错. content={},msg={}", user, e.getMessage(), e);
                try {
                    channel.basicReject(deliveryTag, false);
                } catch (IOException ex) {
                    log.error("change message basicReject error. content={},msg={}", user, ex.getMessage(), ex);
                }
            }
        }
        try{
            esLogMatchDataList.forEach(System.out::println);
            channel.basicAck(deliveryTag,true);
        }catch (Exception ex){
            try {
                channel.basicReject(deliveryTag, false);
            } catch (IOException e) {
                esLogMatchDataList.forEach(esdata->{
                    log.error("change message basicReject error. content={},msg={}", esdata, ex.getMessage(), ex);
                });
            }
        }*/
    }

}
