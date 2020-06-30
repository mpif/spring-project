package com.spring.springmvc.service;

import com.spring.springmvc.model.User;

/**
 * @author: caishengzhi
 * @date: 2017-11-13 15:24
 **/

public interface UserService {

    /**
     * 插入用户信息
     * @param user
     */
    public void insert(User user);

    /**
     * 根据用户id查询用户信息
     * @param id
     * @return
     */
    public User queryById(Long id);

    /**
     * 查询用户
     * @param user
     * @return
     */
    User queryBySelective(User user);


}
