package com.jni;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyh on 2019/3/14.
 */
public class FFmpegJni {
    private static OnAmixProgressListener mOnAmixProgressListener;

    public static void onProgress(int second) {
        if (mOnAmixProgressListener != null && second >= 0) {
            mOnAmixProgressListener.onProgress(second);
        }
    }

    public interface OnAmixProgressListener {
        void onProgress(int second);
    }

    public static void mixAudio(String srcAudioPath, List<String> audioPathList, String outputPath, OnAmixProgressListener onAmixProgressListener) {
        mOnAmixProgressListener = onAmixProgressListener;
        _mixAudio(srcAudioPath, audioPathList, outputPath);
    }

    private static void _mixAudio(String srcAudioPath, List<String> audioPathList, String outputPath) {
        ArrayList<String> commandList = new ArrayList<>();
        commandList.add("ffmpeg");
        commandList.add("-i");
        commandList.add(srcAudioPath);
        for (String audioPath : audioPathList) {
            commandList.add("-i");
            commandList.add(audioPath);
        }
        commandList.add("-filter_complex");
        commandList.add("amix=inputs=" + (audioPathList.size() + 1) + ":duration=first:dropout_transition=1");
        commandList.add("-f");
        commandList.add("mp3");
        commandList.add("-ac");//声道数
        commandList.add("1");
        commandList.add("-ar"); //采样率
        commandList.add("24k");
        commandList.add("-ab");//比特率
        commandList.add("32k");
        commandList.add("-y");
        commandList.add(outputPath);
        String[] commands = new String[commandList.size()];
        commandList.toArray(commands);
        run(commands);
    }

    static {
        System.loadLibrary("avutil-55");
        System.loadLibrary("avcodec-57");
        System.loadLibrary("avformat-57");
        System.loadLibrary("avdevice-57");
        System.loadLibrary("swresample-2");
        System.loadLibrary("swscale-4");
        System.loadLibrary("avfilter-6");
        System.loadLibrary("ffmpeg");
    }

    public static native int run(String[] commands);
}
