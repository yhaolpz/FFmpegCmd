package com.wyh.ffmpegcmd.util;

import android.os.Environment;

/**
 * Created by wyh on 2019/5/12.
 */
public class StorageUtils {

    /**
     * 判断存储卡是否可读
     * @return true可读，false不可读
     */
    public static boolean isExternalStorageReadable() {
        final String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED) || state.equals(Environment.MEDIA_MOUNTED_READ_ONLY);
    }
}
