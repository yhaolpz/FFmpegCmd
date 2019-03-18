package com.wyh.ffmpegcmd.ffmpeg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyh on 2019/3/18.
 */
public class FFmpegVideo {

    public static void mixVideo(List<String> videoPathList, String outputPath, Callback callback) {
        ArrayList<String> commandList = new ArrayList<>();
        commandList.add("ffmpeg");
        for (String audioPath : videoPathList) {
            commandList.add("-i");
            commandList.add(audioPath);
        }
        commandList.add("-filter_complex");
        if (videoPathList.size() == 2) {
            commandList.add("[0:v]pad=iw*2:ih[int];[int][1:v]overlay=W/2:0[vid]");
        } else if (videoPathList.size() == 3) {
            commandList.add("[0:v]pad=iw*2:ih*2[a];[a][1:v]overlay=w[b];[b][2:v]overlay=0:h[vid]");
        } else if (videoPathList.size() == 4) {
            commandList.add("[0:v]pad=iw*2:ih*2[a];[a][1:v]overlay=w[b];[b][2:v]overlay=0:h[c];[c][3:v]overlay=w:h[vid]");
        }
        commandList.add("-map");
        commandList.add("[vid]");
        commandList.add("-c:v");
        commandList.add("libx264");
        commandList.add("-crf");
        commandList.add("23");
        commandList.add("-preset");
        commandList.add("veryfast");
        commandList.add(outputPath);
        FFmpeg.getInstance().run(commandList, callback);
    }
}
