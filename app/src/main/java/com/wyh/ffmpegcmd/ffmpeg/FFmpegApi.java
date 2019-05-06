package com.wyh.ffmpegcmd.ffmpeg;

public class FFmpegApi {
    private static final String TAG = "FFmpegApi";

    /*
    * 获取视频信息步骤：
    * 1.open(String url)
    * 2.getXXX()
    * 3.close
    **/
    public static boolean open(String url) {
        int ret = _open(url);
        return ret >= 0;
    }

    //ms
    public static long getVideoDuration() {
        return _getVideoDuration();
    }

    public static int getVideoWidth() {
        return _getVideoWidth();
    }

    public static int getVideoHeight() {
        return _getVideoHeight();
    }

    public static double getVideoRotation() {
        return _getVideoRotation();
    }

    public static native double _getVideoRotation();

    public static native int _open(String url);

    private static native long _getVideoDuration();

    public static native int _getVideoWidth();

    public static native int _getVideoHeight();

    public static native String _getVideoCodecName();

    public static native void close();

}
