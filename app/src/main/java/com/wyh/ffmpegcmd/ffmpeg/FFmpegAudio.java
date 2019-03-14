package com.wyh.ffmpegcmd.ffmpeg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyh on 2019/3/14.
 */
public class FFmpegAudio {

    public static void mixAudio(String srcAudioPath, List<String> audioPathList, String outputPath, Callback callback) {
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
        FFmpeg.getInstance().run(commandList, callback);
    }
}
