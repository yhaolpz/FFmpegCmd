package com.wyh.ffmpegcmd.util;

import android.widget.Toast;

import com.wyh.ffmpegcmd.common.App;

/**
 * Created by wyh on 2019/3/17.
 */
public class ToastUtil {
    public static void show(String text) {
        Toast.makeText(App.get(), text, Toast.LENGTH_SHORT).show();
    }
}
