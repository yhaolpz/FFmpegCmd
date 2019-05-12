package com.wyh.ffmpegcmd.ffmpeg;

import com.wyh.ffmpegcmd.common.Logger;

/**
 * Created by wyh on 2019/3/12.
 */
public final class FFmpegJni {
    private FFmpegJni() {

    }

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("ffmpeg");
        System.loadLibrary("cmd-ffmpeg");
    }

    public static native int execute(String[] commands);

    public static native String getLog();

}
