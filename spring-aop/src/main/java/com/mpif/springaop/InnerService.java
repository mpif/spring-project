package com.mpif.springaop;

import org.springframework.stereotype.Component;

/**
 * @Author: codefans
 * @Date: 2020-07-01 7:10
 */
@Component
public class InnerService {

    public String getAppName() {
        return "plus.mobile";
    }

}
