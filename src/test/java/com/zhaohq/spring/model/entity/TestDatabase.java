package com.zhaohq.spring.model.entity;

import com.zhaohq.spring.entity.User;
import com.zhaohq.spring.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author zhaohq
 * @date 2020/11/8
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestDatabase {
    @Autowired
    UserService service;
    @Test
    public void user() {
        List<User> list = service.list();
        list.forEach(System.out::println);
    }
}
