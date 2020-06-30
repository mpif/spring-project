/**
 * Copyright (C), 2015-2020, 京东
 * FileName: TestController
 * Author:   caishengzhi
 * Date:     2020/6/30 17:31
 * Description: 测试
 */
package com.mpif.springaop;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * 测试
 *
 * @author caishengzhi
 * @date 2020/06/30 17:31
 * @since 1.0.0
 */
@Component
public class TestController {

    public void test01() {
        System.out.println("TestController.test01().....");
    }

    public void test02() {
        System.out.println("TestController.test02().....");
    }

}