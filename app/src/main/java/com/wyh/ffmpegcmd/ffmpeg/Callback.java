package com.wyh.ffmpegcmd.ffmpeg;

/**
 * Created by wyh on 2019/3/13.
 */
public interface Callback {

    void onLog(String log);

    void onSuccess();

    void onFail();
}
