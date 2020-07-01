package com.mpif.springaop.service;

import org.springframework.stereotype.Component;

/**
 * @Author: codefans
 * @Date: 2020-07-01 8:19
 */
@Component
public class UserService {

    public String queryName(String id) {
        return "this name of id=" + id;
    }
}
