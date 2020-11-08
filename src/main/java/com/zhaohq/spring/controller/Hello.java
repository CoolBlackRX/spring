package com.zhaohq.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaohq
 * @date 2020/11/8
 */
@RestController
public class Hello {

    @GetMapping("/")
    public String hello(){
        return "hello";
    }
}