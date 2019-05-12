package com.wyh.ffmpegcmd.ffmpeg;

public class FFmpegApi {
    private static final String TAG = "FFmpegApi";

    /*
     * 获取视频信息步骤：
     * 1.open(String url)
     * 2.getXXX()
     * 3.close
     **/
    public static boolean openVideo(String url) {
        int ret = open(url);
        return ret >= 0;
    }

    //ms
    public static long getDuration() {
        return getVideoDuration();
    }

    public static int getWidth() {
        return getVideoWidth();
    }

    public static int getHeight() {
        return getVideoHeight();
    }

    public static double getRotation() {
        return getVideoRotation();
    }

    public static native double getVideoRotation();

    public static native int open(String url);

    private static native long getVideoDuration();

    public static native int getVideoWidth();

    public static native int getVideoHeight();

    public static native String getVideoCodecName();

    public static native void close();

}
