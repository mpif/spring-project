/**
 * Copyright (C), 2015-2020, 京东
 * FileName: SpringAspectExclusionProcessor
 * Author:   caishengzhi
 * Date:     2020/6/30 16:27
 * Description: spring切面排除处理类
 */
package com.mpif.springaop;

import com.google.gson.Gson;
import com.mpif.springaop.util.StringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 *
 * spring切面排除处理类
 *
 * @author caishengzhi
 * @date 2020/06/30 16:27
 * @since 1.0.0
 */
@Aspect
@Component
public class SpringAspectExclusionProcessor {
    /**
     * 日志对象
     */
    private static final Logger LOG = LoggerFactory.getLogger(SpringAspectExclusionProcessor.class);

    /**
     * 配置中心服务
     */
    @Autowired
    private DynamicConfigureService dynamicConfigureService;

    /**
     * json工具
     */
    private static final Gson gson = new Gson();

    /**
     * 层切点（类/方法）
     */
//    @Pointcut("execution(* com.jd.*.*(..)) && !execution(* com.jd.vip.plus.common.zk.DynmaicConfigurerService.*(..))")
//    @Pointcut("execution(* com.jd.*.*(..)) && !within(com.jd.vip.plus.common.zk.DynmaicConfigurerService)")
//    @Pointcut("execution(* com.jd.vip.plus.rest..*.*(..))")
//    @Pointcut("execution(* com.jd..*.*(..)) && !bean(dynmaicConfigurerService)")
//    @Pointcut("execution(* com.jd.vip.plus.soa..*.*(..)) || execution(* com.jd.vip.plus.thirdpart..*.*(..)) || execution(* com.jd.vip.plus.service..*.*(..)) || execution(* com.jd.vip.plus.rest..*.*(..)) || execution(* com.jd.vip.plus.mobile..*.*(..)) || execution(* com.jd.vip.plus.controller..*.*(..)) || execution(* com.jd.vip.smc.mobile..*.*(..))")
//    @Pointcut("within(com.jd..*)")
    @Pointcut("cutAllPackages() && !cutDynamicService()")
    public void dynamicLogAspect() {
        //设置层切点
    }

    @Pointcut("execution(* com.mpif..*.*(..))")
    public void cutAllPackages() {

    }

    @Pointcut("execution(* com.mpif.springaop.DynamicConfigureService.*(..))")
    public void cutDynamicService() {

    }

    /**
     * 环绕执行
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("dynamicLogAspect()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
//        LOG.info("running in DynamicLogAspectProcessor.doAround()...........");
        //如果动态日志添加开关关闭，则直接执行目标方法
        if (!dynamicConfigureService.isOpenDynamicLogAdd()) {
            LOG.warn("【动态日志添加】动态日志添加开关已关闭，不执行此逻辑，直接执行目标方法");
            return pjp.proceed();
        }

        Object obj = null;
        boolean needPrintLog = false;
        String currentMethodFullName = "";
        try {

            String dynamicLogMethodConfig = dynamicConfigureService.dynamicLogMethodConfig();

            MethodSignature methodSignature = null;
            Signature signature = pjp.getSignature();
            if(signature instanceof MethodSignature) {
                methodSignature = (MethodSignature) signature;
            }
            if(methodSignature != null) {
                Method method = methodSignature.getMethod();

                StringBuffer sb = new StringBuffer();
                currentMethodFullName = sb.append(method.getDeclaringClass().getCanonicalName()).append(".").append(method.getName()).toString();

                if(dynamicLogMethodConfig != null && dynamicLogMethodConfig.contains(currentMethodFullName)) {
                    needPrintLog = true;
                }
                LOG.info("dynamicLogMethodConfig={}, currentMethodFullName={}, needPrintLog={}", dynamicLogMethodConfig, currentMethodFullName, needPrintLog);

                obj = pjp.proceed();

            }

        } catch (Throwable e) {
            String errorMsg = StringUtil.append("DynamicLogAspectProcessor.doAround异常, 返回值data=").append(gson.toJson(obj)).toString();
            LOG.error(errorMsg, e);
            throw e;
        }

        if(needPrintLog) {
            printLog(currentMethodFullName, this.generateArgs(pjp.getArgs()), obj);
        }

        return obj;
    }


    /**
     * 打印入参
     * @param argsArr
     */
    public String generateArgs(Object[] argsArr) {
        StringBuffer sb = new StringBuffer();
        if(argsArr != null) {
            Object arg = null;
            sb.append("入参[");
            for(int i = 0; i < argsArr.length; i ++) {
                sb.append("arg").append(i+1).append(":[").append(gson.toJson(arg)).append("], ");
            }
        }
        return sb.toString();
    }

    /**
     * 打印入参及返回值
     * @param obj
     */
    public void printLog(String fullMethodName, String paramStr, Object obj) {
        LOG.info("fullMethodName={}, 入参={}, 返回值={}", fullMethodName, paramStr, gson.toJson(obj));
    }

}