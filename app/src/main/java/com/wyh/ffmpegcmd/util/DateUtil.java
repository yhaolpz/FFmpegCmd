package com.wyh.ffmpegcmd.util;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by wyh on 2019/3/18.
 */
public class DateUtil {

    public static final String FORMAT_TIME_EN = "yyyy-MM-dd-HH-mm-ss";

    @StringDef({FORMAT_TIME_EN})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FORMAT {
    }

    private static final SimpleDateFormat SDF = new SimpleDateFormat(FORMAT_TIME_EN, Locale.CHINA);


    public static String format(@FORMAT String timeFormat, Date date) {
        SDF.setTimeZone(TimeZone.getDefault());
        SDF.applyPattern(timeFormat);
        return SDF.format(date);
    }

    public static String format(Date date) {
        return format(FORMAT_TIME_EN, date);
    }


}
