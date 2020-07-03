/**
 * Copyright (C), 2015-2020, 京东
 * FileName: DateUtil
 * Author:   caishengzhi
 * Date:     2020/7/2 14:52
 * Description: 时间处理类
 */
package com.mpif.springaop.util;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * 时间处理类
 *
 * @author caishengzhi
 * @date 2020/07/02 14:52
 * @since 1.0.0
 */
public class DateUtil {

    /**
     * 时间格式
     */
    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 格式化
     * @param date
     * @return
     */
    public static String format(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_PATTERN);
        return sdf.format(date);
    }
}