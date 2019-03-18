package com.wyh.ffmpegcmd.util;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.wyh.ffmpegcmd.R;

/**
 * Created by wyh on 2019/3/18.
 */
public class SnackBarUtil {

    public static void showInfo(View root, String message) {
        Snackbar snackbar = Snackbar.make(root, message, Snackbar.LENGTH_SHORT);
        setSnackBarColor(snackbar, Color.WHITE, Color.BLACK);
        snackbar.show();
    }


    public static void showError(View root, String message) {
        Snackbar snackbar = Snackbar.make(root, message, Snackbar.LENGTH_SHORT);
        setSnackBarColor(snackbar, Color.WHITE, Color.RED);
        snackbar.show();
    }


    /**
     * 设置文字和背景颜色
     */
    public static void setSnackBarColor(Snackbar snackbar, int textColor, int backgroundColor) {
        View view = snackbar.getView();
        view.setBackgroundColor(backgroundColor);
        ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(textColor);
    }

    /**
     * 设置背景颜色
     */
    public static void setSnackBarColor(Snackbar snackbar, int backgroundColor) {
        View view = snackbar.getView();
        view.setBackgroundColor(backgroundColor);
    }
}
