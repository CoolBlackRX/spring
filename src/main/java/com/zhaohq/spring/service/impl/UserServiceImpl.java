package com.zhaohq.spring.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhaohq.spring.entity.User;
import com.zhaohq.spring.mapper.UserMapper;
import com.zhaohq.spring.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author zhaohq
 * @date 2020/11/8
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

}
