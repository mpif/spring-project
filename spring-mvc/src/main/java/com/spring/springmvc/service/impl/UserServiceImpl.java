package com.spring.springmvc.service.impl;

import com.spring.springmvc.mapper.UserMapper;
import com.spring.springmvc.model.User;
import com.spring.springmvc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: caishengzhi
 * @date: 2017-11-13 15:24
 **/
@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public void insert(User user) {
        userMapper.insertSelective(user);
    }

    @Override
    public User queryById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User queryBySelective(User user) {
        return userMapper.queryBySelective(user);
    }
}
