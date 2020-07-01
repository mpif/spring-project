package com.mpif.springaop.included;

import com.mpif.springaop.InnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: codefans
 * @Date: 2020-07-01 7:10
 */
@Component
public class CouponController {

    @Autowired
    private InnerService innerService;

    public String queryCouponList(String activityId) {
        System.out.println(innerService.getAppName());
        return "this is one coupon of activity[" + activityId + "]";
    }

}
