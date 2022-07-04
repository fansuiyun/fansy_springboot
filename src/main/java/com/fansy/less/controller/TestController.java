package com.fansy.less.controller;


import com.fansy.less.constant.Constants;
import com.fansy.less.service.TestService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author Fsy
 */
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
        for (int i = 0; i < 1000; i++) {
            HashMap hashMap=new HashMap();
            hashMap.put("id",i);
            rabbitTemplate.convertAndSend(Constants.topExchange,Constants.routekey,hashMap);
        }
        return id;
    }

}