package com.wyh.ffmpegcmd.ffmpeg;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyh on 2019/3/18.
 */
public class FFmpegVideo {

    private static void setVideo(List<String> commandList) {
        commandList.add("-b:v");
        commandList.add("600k");
        commandList.add("-bufsize");
        commandList.add("600k");
        commandList.add("-maxrate");
        commandList.add("800k");

        commandList.add("-c:v");
        commandList.add("libx264");
        commandList.add("-preset");
        commandList.add("fast");
        commandList.add("-crf");
        commandList.add("28");
        commandList.add("-threads");
        commandList.add("2");

        commandList.add("-y");
        commandList.add("-f");
        commandList.add("mp4");
    }

    private static void setAudio(List<String> commandList) {
        commandList.add("-c:a");
        commandList.add("aac");
        commandList.add("-ar");
        commandList.add("44100");
        commandList.add("-ab");
        commandList.add("48k");
    }

    private static void scale(List<String> commandList) {
        commandList.add("-vf");
        commandList.add("scale=720:720/a");
    }

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
        commandList.add(outputPath);
        FFmpeg.getInstance().run(commandList, callback);
    }

    /**
     * @param pos 0:左上；1右上；2左下；3右下
     */
    @Deprecated
    public static void addWaterMark(String srcVideoPath, String watermarkImgPath, int pos, String outputPath, Callback callback) {
        ArrayList<String> commandList = new ArrayList<>();
        commandList.add("ffmpeg");
        commandList.add("-i");
        commandList.add(srcVideoPath);
        commandList.add("-i");
        commandList.add(watermarkImgPath);
        commandList.add("-filter_complex");

        switch (pos) {
            case 0:
                commandList.add("overlay=10:10");
                break;
            case 1:
                commandList.add("overlay=main_w-overlay_w-10:10");
                break;
            case 2:
                commandList.add("overlay=0:main_h-overlay_h-10");
                break;
            case 3:
                commandList.add("overlay=main_w-overlay_w-10:main_h-overlay_h-10");
                break;
        }
        commandList.add(outputPath);
        FFmpeg.getInstance().run(commandList, callback);
    }

    /**
     * @param pos 0:左上；1右上；2左下；3右下
     */
    public static void addWaterMark2(String srcVideoPath, String watermarkImgPath, int pos, String outputPath, Callback callback) {
        ArrayList<String> commandList = new ArrayList<>();
        commandList.add("ffmpeg");
        commandList.add("-i");
        commandList.add(srcVideoPath);
        commandList.add("-vf");
        String cmd = "movie=";
        cmd += watermarkImgPath;
        cmd += ",scale= 100: 60[watermask]; [in] [watermask] ";
        switch (pos) {
            case 0:
                cmd += "overlay=10:10";
                break;
            case 1:
                cmd += "overlay=main_w-overlay_w-10:10";
                break;
            case 2:
                cmd += "overlay=0:main_h-overlay_h-10";
                break;
            case 3:
                cmd += "overlay=main_w-overlay_w-10:main_h-overlay_h-10";
                break;
        }
        cmd += " [out]";
        commandList.add(cmd);
        commandList.add(outputPath);
        FFmpeg.getInstance().run(commandList, callback);
    }

    /**
     * @param x 去处区域位置
     * @param y 去处区域位置
     * @param w 去处区域宽
     * @param h 去处区域高
     */
    public static void cleanWaterMark(String srcVideoPath, int x, int y, int w, int h, String outputPath, Callback callback) {
        ArrayList<String> commandList = new ArrayList<>();
        commandList.add("ffmpeg");
        commandList.add("-i");
        commandList.add(srcVideoPath);
        commandList.add("-filter_complex");
        String cmd = "delogo=";
        cmd += "x=" + x;
        cmd += ":";
        cmd += "y=" + y;
        cmd += ":";
        cmd += "w=" + w;
        cmd += ":";
        cmd += "h=" + h;
        cmd += ":show=0";
        commandList.add(cmd);
        commandList.add(outputPath);
        FFmpeg.getInstance().run(commandList, callback);
    }

    public static void demuxAudio(String srcVideoPath, String outputPath, Callback callback) {
        ArrayList<String> commandList = new ArrayList<>();
        commandList.add("ffmpeg");
        commandList.add("-i");
        commandList.add(srcVideoPath);
        commandList.add("-acodec");
        commandList.add("copy");
        commandList.add("-vn");
        commandList.add(outputPath);
        FFmpeg.getInstance().run(commandList, callback);
    }

    public static void demuxVideo(String srcVideoPath, String outputPath, Callback callback) {
        ArrayList<String> commandList = new ArrayList<>();
        commandList.add("ffmpeg");
        commandList.add("-i");
        commandList.add(srcVideoPath);
        commandList.add("-vcodec");
        commandList.add("copy");
        commandList.add("-an");
        commandList.add(outputPath);
        FFmpeg.getInstance().run(commandList, callback);
    }

    /**
     * 镜像翻转
     */
    public static void flipVideo(String srcVideoPath, String outputPath, boolean vertical, Callback callback) {
        ArrayList<String> commandList = new ArrayList<>();
        commandList.add("ffmpeg");
        commandList.add("-i");
        commandList.add(srcVideoPath);
        commandList.add("-vf");
        if (vertical) {
            commandList.add("vflip");
        } else {
            commandList.add("hflip");
        }
        commandList.add(outputPath);
        FFmpeg.getInstance().run(commandList, callback);
    }


    @StringDef({
            Filter.filter_1,
            Filter.filter_2,
            Filter.filter_3,
            Filter.filter_4
    })
    @Retention(RetentionPolicy.SOURCE) //滤镜
    public @interface Filter {
        //黑白
        String filter_1 = "lutyuv=u=128:v=128";
        //色彩变换
        String filter_2 = "hue=H=2*PI*t: s=sin(2*PI*t)+1";
        //暗角
        String filter_3 = "vignette=PI/3";
        //底片
        String filter_4 = "lutyuv=y=maxval+minval-val:u=maxval+minval-val:v=maxval+minval-val";
    }

    /**
     * 滤镜
     */
    public static void filterVideo(String srcVideoPath,
                                   @Filter String filter,
                                   String outputPath,
                                   Callback callback) {
        ArrayList<String> commandList = new ArrayList<>();
        commandList.add("ffmpeg");
        commandList.add("-i");
        commandList.add(srcVideoPath);
        setAudio(commandList);
        setVideo(commandList);
        commandList.add("-vf");
        commandList.add(filter);
        commandList.add(outputPath);
        FFmpeg.getInstance().run(commandList, callback);
    }


//    String filter_4 = "rotate=A*sin(2*PI/T*t)";

}
