package com.fansy.less.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Fsy
 */
@Service
public class TestService {

    public List queryList(String id){
        System.out.println(id);
        return new ArrayList();
    }

}
