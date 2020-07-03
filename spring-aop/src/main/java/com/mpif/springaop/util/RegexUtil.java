/**
 * Copyright (C), 2015-2020, 京东
 * FileName: RegexUtil
 * Author:   caishengzhi
 * Date:     2020/7/2 15:25
 * Description: 正则表达式处理类
 */
package com.mpif.springaop.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * 正则表达式处理类
 *
 * @author caishengzhi
 * @date 2020/07/02 15:25
 * @since 1.0.0
 */
public class RegexUtil {

    /**
     * 判断name是否匹配patternStr表达式
     * @param name
     * @param patternStr
     * @return
     */
    public static boolean isMatch(String name, String patternStr) {
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(name);
        return matcher.find();
    }

    public static void main(String[] args) {

        System.out.println(isMatch("DifferentAdvicePointcut", "DifferentAdvicePointcut"));

    }

}