/**
 * Copyright (C), 2015-2020, 京东
 * FileName: DifferentAdviceProcessor
 * Author:   caishengzhi
 * Date:     2020/7/2 14:38
 * Description: 测试不同advice
 */
package com.mpif.springaop;


import com.mpif.springaop.util.DateUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 *
 * 测试不同advice
 *
 * @author caishengzhi
 * @date 2020/07/02 14:38
 * @since 1.0.0
 */
@Aspect
@Component
public class DifferentAdviceProcessor {

    private Logger log = LoggerFactory.getLogger(DifferentAdviceProcessor.class);

//    @Pointcut("within(com.mpif.springaop.DifferentAdvicePointcut)")
//    @Pointcut("target(com.mpif.springaop.DifferentAdvicePointcut)")
    @Pointcut("this(com.mpif.springaop.DifferentAdvicePointcut)")
    public void cutDifferentAdvicePointcut() {

    }

    @Before("cutDifferentAdvicePointcut()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("running in Before, time:" + DateUtil.format(new Date()));
    }

    @Around("cutDifferentAdvicePointcut()")
    public void doAround(ProceedingJoinPoint proceedingJoinPoint) {
        log.info("running in Around, time:" + DateUtil.format(new Date()));
    }

    @After("cutDifferentAdvicePointcut()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("running in After, time:" + DateUtil.format(new Date()));
    }

    @AfterReturning("cutDifferentAdvicePointcut()")
    public void doAfterReturning(JoinPoint joinPoint) {
        log.info("running in AfterReturning, time:" + DateUtil.format(new Date()));
    }

    @AfterThrowing("cutDifferentAdvicePointcut()")
    public void doAfterThrowing(JoinPoint joinPoint) {
        log.info("running in AfterThrowing, time:" + DateUtil.format(new Date()));
    }


}