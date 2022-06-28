package com.fansy.less.controller;


import com.fansy.less.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fsy
 */
@RestController
@RequestMapping("/less")
public class TestController {

    private TestService testService;

    public TestController(){

    }
    @Autowired
    public TestController(TestService testService){
        this.testService = testService;
    }

    @RequestMapping("/{id}")
    public String test(@PathVariable String id){
        testService.queryList(id);
        return id;
    }

}
