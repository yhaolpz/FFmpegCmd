package com.wyh.ffmpegcmd.edit.audio;

import android.view.Menu;

import com.wyh.ffmpegcmd.edit.EditMediaListActivity;
import com.wyh.ffmpegcmd.edit.MediaFile;
import com.wyh.ffmpegcmd.ffmpeg.Callback;
import com.wyh.ffmpegcmd.ffmpeg.FFmpegAudio;
import com.wyh.ffmpegcmd.util.DateUtil;
import com.wyh.ffmpegcmd.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AudioMixActivity extends EditMediaListActivity {

    public static final String TITLE = "混音";

    @Override
    protected String getEditTitle() {
        return TITLE;
    }

    @Override
    protected void createOptionsMenu(Menu menu) {
        menu.add("添加音频");
        menu.add("删除音频");
        menu.add("开始");
    }

    @Override
    protected void onMenuClick(int order) {
        if (order == 0) {
            pickAudio();
        } else if (order == 1) {
            deleteLastMediaFile();
        } else if (order == 2) {
            if (mMediaFileList.size() < 2) {
                showErrorSnack("请添加音频");
                return;
            }
            run(mMediaFileList);
        }
    }

    private void run(List<MediaFile> mediaFileList) {
        showLoadingDialog();
        final String outputAudio = FileUtil.OUTPUT_AUDIO_DIR + File.separator +
                "mix_" + DateUtil.format(new Date()) + ".mp3";
        List<String> audioPaths = new ArrayList<>();
        for (int i = 0; i < mediaFileList.size(); i++) {
            if (i == 0) {
                continue;
            }
            audioPaths.add(mediaFileList.get(i).getPath());
        }
        FFmpegAudio.mixAudio(mediaFileList.get(0).getPath(), audioPaths, outputAudio, new Callback() {
            @Override
            public void onSuccess() {
                dismissLoadingDialog();
                showSaveDoneAndPlayDialog(outputAudio, false);
            }

            @Override
            public void onFail() {
                dismissLoadingDialog();
                showErrorSnack("合成失败");
            }
        });
    }
}