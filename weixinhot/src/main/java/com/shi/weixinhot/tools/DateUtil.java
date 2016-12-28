package com.shi.weixinhot.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by shimanqiang on 16/12/28.
 */

public class DateUtil {

    /**
     * JAVA判断两个时间戳，相隔多少分钟、小时、等
     *
     * @param str
     * @return
     */
    public static String transferStampGap(String str) {
        Long minute = (System.currentTimeMillis() - new Long(str)) / (1000 * 60);
        Long hour = 0L;
        Long day = 0L;
        Long year = 0L;

        String retStr = minute + "分钟前";

        if (minute != 0) {
            hour = minute / 60;
            if (hour > 0) {
                retStr = hour + "小时前";
            }
        }

        if (hour != 0) {
            day = hour / 24;
            if (day > 0) {
                retStr = hour + "天前";
            }
        }

        if (day != 0) {
            year = day / 365;
            if (year > 0) {
                retStr = year + "年前";
            }
        }

        return retStr;
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = sdf.format(date);
        return res;
    }
}
