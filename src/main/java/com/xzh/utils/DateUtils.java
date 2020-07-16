package com.xzh.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具
 *
 * @author: 向振华
 * @date: 2019/12/23 17:54
 */
public class DateUtils {

    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_SHORT = "yyyyMMddHHmmss";
    public static final String DATE = "yyyy-MM-dd";
    public static final String TIME = "HH:mm:ss";
    public static final String CRON = "ss mm HH dd MM ? yyyy";

    /**
     * 日期转字符串
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(DATE_TIME);
        return format.format(date);
    }

    /**
     * 日期转字符串，根据pattern转换
     * @param date
     * @param pattern
     * @return
     */
    public static String dateToString(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        if (pattern == null || pattern.length() == 0){
            pattern = DATE_TIME;
        }
        try {
            return new SimpleDateFormat(pattern).format(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 字符串转日期
     * @param dateString
     * @return
     */
    public static Date stringToDate(String dateString) {
        if (dateString == null || dateString.length() == 0) {
            return null;
        }
        try {
            return new SimpleDateFormat(DATE_TIME).parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 字符串转日期，根据pattern转换
     * @param dateString
     * @param pattern
     * @return
     */
    public static Date stringToDate(String dateString, String pattern) {
        if (dateString == null || dateString.length() == 0) {
            return null;
        }
        if (pattern == null || pattern.length() == 0){
            pattern = DATE_TIME;
        }
        try {
            return new SimpleDateFormat(pattern).parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }
}