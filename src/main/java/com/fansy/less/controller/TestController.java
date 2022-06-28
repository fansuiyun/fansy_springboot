package com.fansy.less.controller;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fsy
 */
@RestController
@RequestMapping("/less")
public class TestController {

    @RequestMapping("/{id}")
    public String test(@PathVariable String id){
        System.out.println(id);
        return id;
    }

}
