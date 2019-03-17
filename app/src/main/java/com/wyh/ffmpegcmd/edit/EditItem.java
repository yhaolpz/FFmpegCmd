package com.wyh.ffmpegcmd.edit;

import android.support.annotation.DrawableRes;

/**
 * Created by wyh on 2019/3/17.
 */
public class EditItem {
    @DrawableRes
    public final int imgRes;

    public final String text;

    public final Class activityClass;

    public EditItem(int imgRes, String text, Class activityClass) {
        this.imgRes = imgRes;
        this.text = text;
        this.activityClass = activityClass;
    }

    @Override
    public String toString() {
        return "EditItem{" +
                "imgRes=" + imgRes +
                ", text='" + text + '\'' +
                ", activityClass=" + activityClass +
                '}';
    }
}
