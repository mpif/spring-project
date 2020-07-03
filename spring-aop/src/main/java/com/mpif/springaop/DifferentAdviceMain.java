/**
 * Copyright (C), 2015-2020, 京东
 * FileName: DifferentAdviceMain
 * Author:   caishengzhi
 * Date:     2020/7/2 14:42
 * Description: 不同advice测试主程序
 */
package com.mpif.springaop;


import com.mpif.springaop.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;

/**
 *
 * 不同advice测试主程序
 *
 * @author caishengzhi
 * @date 2020/07/02 14:42
 * @since 1.0.0
 */
public class DifferentAdviceMain {

    /**
     * 日志
     */
    private Logger log = LoggerFactory.getLogger(DifferentAdviceMain.class);

    public static void main(String[] args) {
        DifferentAdviceMain differentAdviceMain = new DifferentAdviceMain();
        differentAdviceMain.startup();
    }

    /**
     * 启动
     */
    public void startup() {

        String propFile = System.getProperty("user.dir") + File.separator + "spring-aop" + File.separator + "src/main/resources" + File.separator + "log4j2.xml";
        //初始化方式1
        System.setProperty("log4j.configurationFile", propFile);

        String configFile = "spring-context.xml";
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(configFile);
        DifferentAdvicePointcut differentAdvicePointcut = (DifferentAdvicePointcut) applicationContext.getBean("differentAdvicePointcut");
        String param = "张三zhangsan";
        String result = differentAdvicePointcut.execute(param);
        log.info("param={}, result={}", param, result);

        UserService userService = (UserService) applicationContext.getBean("userService");
        String userId = "30003";
        String userInfo = userService.queryName(userId);
        log.info("userId=" + userId + ", userInfo=" + userInfo);

    }

}