/**
 * Copyright (C), 2015-2020, 京东
 * FileName: DifferentAdvicePointcut
 * Author:   caishengzhi
 * Date:     2020/7/2 14:41
 * Description: 测试advice的pointcut
 */
package com.mpif.springaop;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * 测试advice的pointcut
 *
 * @author caishengzhi
 * @date 2020/07/02 14:41
 * @since 1.0.0
 */
@Component
public class DifferentAdvicePointcut {

    private Logger log = LoggerFactory.getLogger(DifferentAdvicePointcut.class);

    public String execute(String param) {
        log.info("running in DifferentAdvicePointcut.execute()........");
        return "hello " + param;
    }

}