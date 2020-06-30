package com.spring.springmvc.mapper;

import com.spring.springmvc.model.User;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    User queryBySelective(User user);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}