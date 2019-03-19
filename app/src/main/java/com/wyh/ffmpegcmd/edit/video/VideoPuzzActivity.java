package com.wyh.ffmpegcmd.edit.video;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.wyh.ffmpegcmd.R;
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

public class VideoPuzzActivity extends BaseEditActivity {

    public static final String TITLE = "视频拼图";

    private View mRoot;
    private RecyclerView mRecyclerView;
    private ItemMediaAdapter mAdapter;
    private List<MediaFile> mMediaFileList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_puzz);
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
    protected void onMenuClick(int order) {
        if (order == 0) {
            if (mMediaFileList.size() == 4) {
                SnackBarUtil.showError(mRoot, "最多支持四路视频拼接");
                return;
            }
            pickVideo();
        } else if (order == 1) {
            if (mMediaFileList.size() < 1) {
                return;
            }
            mMediaFileList.remove(mMediaFileList.size() - 1);
            mAdapter.notifyItemRemoved(mMediaFileList.size());
        } else if (order == 2) {
            if (mMediaFileList.size() < 2) {
                SnackBarUtil.showError(mRoot, "请添加视频");
                return;
            }
            run(mMediaFileList);
        }
    }

    @Override
    protected void createOptionsMenu(Menu menu) {
        menu.add("添加视频");
        menu.add("删除视频");
        menu.add("开始");
    }


    @Override
    protected void onPickFile(@NonNull MediaFile mediaFile) {
        super.onPickFile(mediaFile);
        mMediaFileList.add(mediaFile);
        mAdapter.notifyItemInserted(mMediaFileList.size());
    }

    private void run(List<MediaFile> mediaFileList) {
        showLoadingDialog();
        final String output = FileUtil.OUTPUT_VIDEO_DIR + File.separator +
                "mix_" + DateUtil.format(new Date()) + ".mp4";
        List<String> audioPaths = new ArrayList<>();
        for (int i = 0; i < mediaFileList.size(); i++) {
            audioPaths.add(mediaFileList.get(i).getPath());
        }
        FFmpegVideo.mixVideo(audioPaths, output, new Callback() {
            @Override
            public void onSuccess() {
                dismissLoadingDialog();
                showSaveDoneAndPlayDialog(output, true);
            }

            @Override
            public void onFail() {
                dismissLoadingDialog();
                SnackBarUtil.showError(mRoot, "合成失败");
            }
        });
    }


}
