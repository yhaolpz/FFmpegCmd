package com.wyh.ffmpegcmd.edit.audio;

import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.wyh.ffmpegcmd.R;
import com.wyh.ffmpegcmd.edit.BaseEditActivity;
import com.wyh.ffmpegcmd.edit.ItemMediaAdapter;
import com.wyh.ffmpegcmd.edit.MediaFile;
import com.wyh.ffmpegcmd.ffmpeg.Callback;
import com.wyh.ffmpegcmd.ffmpeg.FFmpegAudio;
import com.wyh.ffmpegcmd.util.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AudioMixActivity extends BaseEditActivity {

    public static final String TITLE = "混音";

    private RecyclerView mRecyclerView;
    private ItemMediaAdapter mAdapter;
    private List<MediaFile> mMediaFileList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_mix);
        mRecyclerView = findViewById(R.id.recycleView);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        menu.clear();
        menu.add("添加音频");
        menu.add("删除音频");
        menu.add("开始");
        return true;
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
                ToastUtil.show("请添加音频");
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

        String dir = getCacheDir().getAbsolutePath();
        final String outputAudio = dir + File.separator + "output.mp3";

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
//                playAudio(outputAudio);
            }

            @Override
            public void onFail() {
                dismissLoadingDialog();
            }
        });

    }


}
