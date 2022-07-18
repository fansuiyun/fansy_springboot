package com.fansy.less.config;

import com.fansy.less.constant.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Fsy
 */
@Configuration
public class RabbitmqConfig {

    @Bean
    public RabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory,ObjectMapper objectMapper){
        SimpleRabbitListenerContainerFactory factory=new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setBatchListener(true);
        factory.setConsumerBatchEnabled(true);
        factory.setBatchSize(20);
        factory.setPrefetchCount(30);
        factory.setMessageConverter(messageConverter(objectMapper));
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper){
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public Queue queue(){
        return new Queue(Constants.queue);
    }

    @Bean
    public TopicExchange exchange (){
        return new TopicExchange(Constants.topExchange);
    }

    @Bean
    Binding binding(){
        return BindingBuilder.bind(queue()).to(exchange()).with(Constants.routekey);
    }
}
