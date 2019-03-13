package com.wyh.ffmpegcmd.ffmpeg.Jni;

/**
 * Created by wyh on 2019/3/12.
 */
public class FFmpegJni {
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("avcodec-57");
        System.loadLibrary("avdevice-57");
        System.loadLibrary("avfilter-6");
        System.loadLibrary("avformat-57");
        System.loadLibrary("avutil-55");
        System.loadLibrary("swresample-2");
        System.loadLibrary("swscale-4");
        System.loadLibrary("cmd-ffmpeg");
    }

    public static native int excute(String[] commands);

}
