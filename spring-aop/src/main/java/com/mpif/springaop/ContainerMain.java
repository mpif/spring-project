/**
 * Copyright (C), 2015-2020, 京东
 * FileName: ContainerMain
 * Author:   caishengzhi
 * Date:     2020/6/30 16:27
 * Description: 容器启动
 */
package com.mpif.springaop;


import com.mpif.springaop.excluded.ActivityController;
import com.mpif.springaop.included.CouponController;
import com.mpif.springaop.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 容器启动
 *
 * @author caishengzhi
 * @date 2020/06/30 16:27
 * @since 1.0.0
 */
public class ContainerMain {

    public static void main(String[] args) {
        ContainerMain containerMain = new ContainerMain();
        containerMain.startup();
    }

    public void startup() {


        String propFile = System.getProperty("user.dir") + File.separator + "spring-aop" + File.separator + "src/main/resources" + File.separator + "log4j2.xml";
        //初始化方式1
        System.setProperty("log4j.configurationFile", propFile);

        String configFile = "spring-context.xml";
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(configFile);
        TestController testController = (TestController) applicationContext.getBean("testController");
        testController.test01();

        UserService userService = (UserService) applicationContext.getBean("userService");
        String userId = "30003";
        String userInfo = userService.queryName(userId);
        System.out.println("userId=" + userId + ", userInfo=" + userInfo);


        ActivityController activityController = (ActivityController) applicationContext.getBean("activityController");
        String activityId = "10001";
        String activityContent = activityController.queryActivity(activityId);
        System.out.println("activityId=" + activityId + ", activityContent=" + activityContent);

        CouponController couponController = (CouponController) applicationContext.getBean("couponController");
        activityId = "20003";
        List<String> couponIdList = new ArrayList<>();
        couponIdList.add("couponId001");
        couponIdList.add("couponId002");
        couponIdList.add("couponId003");
        String couponInfo = couponController.queryCouponList(activityId, couponIdList);
        System.out.println("activityId=" + activityId + ", couponInfo=" + couponInfo);

//        while(true) {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

    }

}