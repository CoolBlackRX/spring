package com.zhaohq.spring.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhaohq.spring.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author zhaohq
 * @date 2020/11/8
 */
@Mapper
@Component
public interface UserMapper extends BaseMapper<User> {
}
