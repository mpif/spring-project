package com.mpif.springaop.excluded;

import com.mpif.springaop.InnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: codefans
 * @Date: 2020-07-01 7:10
 */
@Component
public class ActivityController {

    @Autowired
    private InnerService innerService;

    public String queryActivity(String activityId) {
        System.out.println(innerService.getAppName());
        return "this is activity's id=[" + activityId + "]";
    }

}
