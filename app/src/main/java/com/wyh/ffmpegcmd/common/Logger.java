package com.wyh.ffmpegcmd.common;

import android.util.Log;

/**
 * Created by wyh on 2019/3/18.
 */
public class Logger {
    private static final String TAG = "FFmpegCmd_Logger";

    public static void d(String tag, String message) {
        Log.d(tag, message);
    }


    public static void e(String tag, String message, Throwable throwable) {
        Log.e(tag, message, throwable);
    }

    public static void d(String message) {
        Log.d(TAG, message);
    }


    public static void e(String message, Throwable throwable) {
        Log.e(TAG, message, throwable);
    }
}
