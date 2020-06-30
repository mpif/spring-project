/**
 * Copyright (C), 2015-2020, 京东
 * FileName: ContainerMain
 * Author:   caishengzhi
 * Date:     2020/6/30 16:27
 * Description: 容器启动
 */
package com.mpif.springaop;


import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;

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

        System.getProperty("user.dir");

        public static String OPEN_SOURCE_ROOT  = PROJECT_ROOT + File.separator + "open-source";

        public static String OPEN_SOURCE_RESOURCES_ROOT  = OPEN_SOURCE_ROOT + File.separator + "src/main/resources";

        public static String OPEN_SOURCE_RESOURCES_LOG4J  = OPEN_SOURCE_RESOURCES_ROOT + File.separator + "log4j";

        String propFile = 
        //初始化方式1
        System.setProperty("log4j.configurationFile", propFile);

        String configFile = "spring-context.xml";
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(configFile);
        TestController testController = (TestController) applicationContext.getBean("testController");
        testController.test01();

        while(true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}