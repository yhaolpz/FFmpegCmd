package com.wyh.ffmpegcmd.edit.audio;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;

import com.wyh.ffmpegcmd.R;
import com.wyh.ffmpegcmd.common.SecureAlertDialog;
import com.wyh.ffmpegcmd.edit.BaseEditActivity;
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

public class AudioMixActivity extends BaseEditActivity {

    public static final String TITLE = "混音";

    private View mRoot;
    private RecyclerView mRecyclerView;
    private ItemMediaAdapter mAdapter;
    private List<MediaFile> mMediaFileList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_mix);
        mRecyclerView = findViewById(R.id.recycleView);
        mRoot = findViewById(R.id.root);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMediaFileList = new ArrayList<>();
        mAdapter = new ItemMediaAdapter(mMediaFileList);
        mRecyclerView.setAdapter(mAdapter);
    }


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
            if (mMediaFileList.size() < 1) {
                return;
            }
            mMediaFileList.remove(mMediaFileList.size() - 1);
            mAdapter.notifyItemRemoved(mMediaFileList.size());
        } else if (order == 2) {
            if (mMediaFileList.size() < 2) {
                SnackBarUtil.showError(mRoot, "请添加音频");
                return;
            }
            run(mMediaFileList);
        }
    }

    @Override
    protected void onPickFile(@NonNull MediaFile mediaFile) {
        super.onPickFile(mediaFile);
        mMediaFileList.add(mediaFile);
        mAdapter.notifyItemInserted(mMediaFileList.size());
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
                SnackBarUtil.showError(mRoot, "合成失败");
            }
        });
    }
}