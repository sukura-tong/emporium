package com.swust.emporium.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 雪瞳
 * @Slogan 忘时，忘物，忘我。
 * @Function
 * 格式化日期类
 */
public class DateUtils {

    public static String getDateToString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-");
        String format = sdf.format(date);
        return format;
    }
}
