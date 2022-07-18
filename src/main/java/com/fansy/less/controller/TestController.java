package com.fansy.less.controller;


import com.fansy.less.constant.Constants;
import com.fansy.less.entity.User;
import com.fansy.less.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fsy
 */
@Slf4j
@RestController
@RequestMapping("/less")
public class TestController {

    @Autowired
    private TestService testService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/{id}")
    public String test(@PathVariable String id){
        testService.queryList(id);
        for (int i = 0; i < 40; i++) {
            User user = new User();
            user.setId(i);
            user.setName("fansy"+i);
            rabbitTemplate.convertAndSend(Constants.topExchange,Constants.routekey,user);
        }
        return id;
    }

}
