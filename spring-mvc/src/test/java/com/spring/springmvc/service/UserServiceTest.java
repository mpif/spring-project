package com.spring.springmvc.service;

import com.spring.springmvc.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author: caishengzhi
 * @date: 2017-11-13 15:29
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:web-context.xml")
@TestPropertySource(properties={"env=dev"})
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void insertUserTest() {

        User user = new User();
        user.setUsername("张三");
        user.setPassword("111");
        userService.insert(user);


    }

    @Test
    public void queryUserTest() {
        Long id = 1L;
        User user = userService.queryById(id);
        if(user != null) {
            System.out.println("根据ID查询到的用户为:[" + user.toString() + "]");
        } else {
            System.out.println("user is null");
        }

        User queryUser = new User();
        user.setUsername("张三");
        user.setPassword("111");
        User userResult = userService.queryBySelective(queryUser);
        if(userResult != null) {
            System.out.println("根据用户信息查询到的用户为:[" + userResult.toString() + "]");
        } else {
            System.out.println("userResult is null");
        }



    }

}
