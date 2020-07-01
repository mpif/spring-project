/**
 * Copyright (C), 2015-2020, 京东
 * FileName: DynamicConfigureService
 * Author:   caishengzhi
 * Date:     2020/6/30 16:53
 * Description: 动态配置服务
 */
package com.mpif.springaop;


import org.springframework.stereotype.Component;

/**
 *
 * 动态配置服务
 *
 * @author caishengzhi
 * @date 2020/06/30 16:53
 * @since 1.0.0
 */
@Component
public class DynamicConfigureService {

    /**
     *
     * @return
     */
    public boolean isOpenDynamicLogAdd() {
        return true;
    }

    /**
     *
     * @return
     */
    public String dynamicLogMethodConfig() {
        return "com.mpif.springaop.TestController.test01;com.mpif.springaop.included.CouponController.queryCouponList;";
    }

}