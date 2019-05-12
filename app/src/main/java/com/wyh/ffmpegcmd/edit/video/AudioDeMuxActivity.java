package com.wyh.ffmpegcmd.edit.video;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;

import com.wyh.ffmpegcmd.R;
import com.wyh.ffmpegcmd.edit.BaseEditActivity;
import com.wyh.ffmpegcmd.edit.EditMediaListActivity;
import com.wyh.ffmpegcmd.edit.ItemMediaAdapter;
import com.wyh.ffmpegcmd.edit.MediaFile;
import com.wyh.ffmpegcmd.ffmpeg.Callback;
import com.wyh.ffmpegcmd.ffmpeg.FFmpegAudio;
import com.wyh.ffmpegcmd.ffmpeg.FFmpegVideo;
import com.wyh.ffmpegcmd.util.DateUtil;
import com.wyh.ffmpegcmd.util.FileUtil;
import com.wyh.ffmpegcmd.util.SnackBarUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AudioDeMuxActivity extends EditMediaListActivity {
    private static final String TAG = "VideoDeMuxActivity";
    public static final String TITLE = "音频分离";


    @Override
    protected String getEditTitle() {
        return TITLE;
    }

    @Override
    protected void createOptionsMenu(Menu menu) {
        menu.add("选择视频");
        menu.add("删除");
        menu.add("开始");
    }

    @Override
    protected void onMenuClick(int order) {
        if (order == 0) {
            if (mMediaFileList.size() == 1) {
                SnackBarUtil.showError(mRoot, "已选择视频");
                return;
            }
            pickVideo();
        } else if (order == 1) {
            deleteLastMediaFile();
        } else {
            if (mMediaFileList.size() == 0) {
                SnackBarUtil.showError(mRoot, "请选择视频");
                return;
            }
            run();
        }
    }

    private void run() {
        showLoadingDialog();
        final String output = FileUtil.OUTPUT_AUDIO_DIR + File.separator +
                "demux_" + DateUtil.format(new Date()) + ".aac";
        FFmpegVideo.demuxAudio(mMediaFileList.get(0).getPath(), output, new Callback() {
            @Override
            public void onSuccess() {
                dismissLoadingDialog();
                showSaveDoneAndPlayDialog(output, false);
            }
            @Override
            public void onLog(String log) {

            }

            @Override
            public void onFail() {
                dismissLoadingDialog();
            }
        });
    }


}
