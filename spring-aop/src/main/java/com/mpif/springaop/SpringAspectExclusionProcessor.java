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
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
//    @Pointcut("cutAllPackages() && !cutDynamicService()")
//    @Pointcut("cutAllPackages()")
    @Pointcut("cutAllPackages() && !cutDynamicService() && !cutExcludedService()")
//    @Pointcut("cutIncludedPackages() && cutServicePackages() && !cutDynamicService() && !cutExcludedService()")
    public void dynamicLogAspect() {
        //设置层切点
    }

    @Pointcut("execution(* com.mpif.springaop..*.*(..))")
    public void cutAllPackages() {

    }

//    @Pointcut("execution(* com.mpif.springaop.included..*.*(..))")
    @Pointcut("within(com.mpif.springaop.included..*)")
    public void cutIncludedPackages() {

    }

//    @Pointcut("execution(* com.mpif.springaop.service..*.*(..))")
    @Pointcut("within(com.mpif.springaop.service..*)")
    public void cutServicePackages() {

    }

    @Pointcut("execution(* com.mpif.springaop.DynamicConfigureService.*(..))")
    public void cutDynamicService() {

    }

    @Pointcut("execution(* com.mpif.springaop.excluded..*.*(..))")
    public void cutExcludedService() {

    }

    @Before("dynamicLogAspect()")
    public Object doBefore(JoinPoint joinPoint) {
        Object obj = null;
        Class[] paramTypes = null;

        try {
            MethodSignature methodSignature = null;
            Signature signature = joinPoint.getSignature();
            if(signature instanceof MethodSignature) {
                methodSignature = (MethodSignature) signature;
            }
            if(methodSignature != null) {
                Method method = methodSignature.getMethod();
                paramTypes = methodSignature.getParameterTypes();

                String paramStr = this.generateArgs(joinPoint.getArgs(), paramTypes);
                System.out.println("doBefore--->paramStr:" + paramStr);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return obj;
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
        String fullName = "";
        String className = "";
        String methodName = "";
        Class[] paramTypes = null;

        try {

            String dynamicLogMethodConfig = dynamicConfigureService.dynamicLogMethodConfig();

            MethodSignature methodSignature = null;
            Signature signature = pjp.getSignature();
            if(signature instanceof MethodSignature) {
                methodSignature = (MethodSignature) signature;
            }
            if(methodSignature != null) {
                Method method = methodSignature.getMethod();
                paramTypes = methodSignature.getParameterTypes();

                className = StringUtil.append(method.getDeclaringClass().getCanonicalName()).toString();
                methodName = method.getName();
                fullName = StringUtil.append(className).append(".").append(methodName).toString();

                needPrintLog = this.needPrintLog(dynamicLogMethodConfig, fullName, className);
                LOG.info("dynamicLogMethodConfig={}, fullName={}, needPrintLog={}", dynamicLogMethodConfig, fullName, needPrintLog);

                obj = pjp.proceed();

            }

        } catch (Throwable e) {
            String errorMsg = StringUtil.append("DynamicLogAspectProcessor.doAround异常, 返回值data=").append(gson.toJson(obj)).toString();
            LOG.error(errorMsg, e);
            throw e;
        }

        if(needPrintLog) {
            printLog(fullName, this.generateArgs(pjp.getArgs(), paramTypes), obj);
        }

        return obj;
    }

    @After("dynamicLogAspect()")
    public Object doAfter(JoinPoint joinPoint) {
        System.out.println("doAfter");
        return null;
    }

    @AfterReturning("dynamicLogAspect()")
    public Object doAfterReturning(JoinPoint joinPoint) {
        System.out.println("doAfterReturning");
        return null;
    }

    @AfterThrowing("dynamicLogAspect()")
    public Object doAfterThrowing(JoinPoint joinPoint) {
        System.out.println("doAfterThrowing");
        return null;
    }

    /**
     * 判断是否需要打印日志
     * @param dynamicLogMethodConfig
     * @param fullName
     * @param className
     * @return
     */
    private boolean needPrintLog(String dynamicLogMethodConfig, String fullName, String className) {
        boolean needPrintLog = false;
        if(!StringUtils.isEmpty(dynamicLogMethodConfig)) {

            /**
             * 如果配置的是类
             */
//            if(dynamicLogMethodConfig.contains(className)) {
//                needPrintLog = true;
//            }
            /**
             * 如果配置的是方法
             */
            if(dynamicLogMethodConfig.contains(fullName)) {
                needPrintLog = true;
            }
        }
        return needPrintLog;
    }

    /**
     * 打印入参
     * @param argsArr
     */
    public String generateArgs(Object[] argsArr, Class[] paramTypes) {
        StringBuffer sb = new StringBuffer();
        if(argsArr != null) {
            Object arg;
            if(argsArr.length > 0) {
                for (int i = 0; i < argsArr.length; i++) {
                    arg = argsArr[i];
                    if(i != 0) {
                        sb.append(", ");
                    }
//                    if(arg instanceof HttpServletRequest || arg instanceof HttpServletResponse) {
//                        continue;
//                    }
//                    sb.append("arg").append(i + 1).append(":[").append(arg.getClass()).append(".").append(paramTypes[i]).append(".").append(JSON.toJSON(arg)).append("]");
                    sb.append("arg").append(i + 1).append(":[").append(gson.toJson(arg)).append("]");
                }
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