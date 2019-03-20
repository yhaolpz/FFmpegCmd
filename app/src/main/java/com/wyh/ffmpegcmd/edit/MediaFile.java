package com.wyh.ffmpegcmd.edit;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by wyh on 2019/3/17.
 * 多媒体文件
 */
public class MediaFile {
    public static final int TYPE_IMG = 1;
    public static final int TYPE_AUDIO = 2;
    public static final int TYPE_VIDEO = 3;

    @IntDef({TYPE_IMG, TYPE_AUDIO, TYPE_VIDEO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    private String name;
    private String path;
    private String thumb;

    @Type
    private int type;

    public void setType(@Type int type) {
        this.type = type;
    }

    @Type
    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
