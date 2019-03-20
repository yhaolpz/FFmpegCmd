package com.wyh.ffmpegcmd.edit.video;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;

import com.wyh.ffmpegcmd.R;
import com.wyh.ffmpegcmd.edit.BaseEditActivity;
import com.wyh.ffmpegcmd.edit.ItemMediaAdapter;
import com.wyh.ffmpegcmd.edit.MediaFile;
import com.wyh.ffmpegcmd.ffmpeg.Callback;
import com.wyh.ffmpegcmd.ffmpeg.FFmpegVideo;
import com.wyh.ffmpegcmd.util.DateUtil;
import com.wyh.ffmpegcmd.util.FileUtil;
import com.wyh.ffmpegcmd.util.SnackBarUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wyh on 2019/3/18.
 */
public class VideoWaterMarkActivity extends BaseEditActivity {
    private static final String TAG = "VideoWaterMarkActivity";
    public static final String TITLE = "加水印";

    private View mRoot;
    private RecyclerView mRecyclerView;
    private ItemMediaAdapter mAdapter;
    private List<MediaFile> mMediaFileList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_mark);
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
        menu.add("选择视频");
        menu.add("选择水印");
        menu.add("删除");
        menu.add("生成左上角水印");
        menu.add("生成右上角水印");
        menu.add("生成左下角水印");
        menu.add("生成右下角水印");
    }

    @Override
    protected void onMenuClick(int order) {
        if (order == 0) {
            for (MediaFile file : mMediaFileList) {
                if (file.getType() == MediaFile.TYPE_VIDEO) {
                    SnackBarUtil.showError(mRoot, "已经选择视频了");
                    return;
                }
            }
            pickVideo();
        } else if (order == 1) {
            for (MediaFile file : mMediaFileList) {
                if (file.getType() == MediaFile.TYPE_IMG) {
                    SnackBarUtil.showError(mRoot, "已经选择水印了");
                    return;
                }
            }
            pickImg();
        } else if (order == 2) {
            if (mMediaFileList.size() < 1) {
                return;
            }
            mMediaFileList.remove(mMediaFileList.size() - 1);
            mAdapter.notifyItemRemoved(mMediaFileList.size());
        } else {
            if (mMediaFileList.size() < 2) {
                SnackBarUtil.showError(mRoot, "未选择视频/水印");
                return;
            }
            showLoadingDialog();
            MediaFile video = null;
            MediaFile img = null;
            for (MediaFile file : mMediaFileList) {
                if (file.getType() == MediaFile.TYPE_IMG) {
                    img = file;
                }
                if (file.getType() == MediaFile.TYPE_VIDEO) {
                    video = file;
                }
            }
            final String output = FileUtil.OUTPUT_VIDEO_DIR + File.separator +
                    "watermark_" + DateUtil.format(new Date()) + ".mp4";
            FFmpegVideo.addWaterMark2(video.getPath(), img.getPath(), order - 3, output, new Callback() {
                @Override
                public void onSuccess() {
                    dismissLoadingDialog();
                    showSaveDoneAndPlayDialog(output, true);
                }

                @Override
                public void onFail() {
                    dismissLoadingDialog();
                }
            });
        }
    }

    @Override
    protected void onPickFile(@NonNull MediaFile mediaFile) {
        super.onPickFile(mediaFile);
        mMediaFileList.add(mediaFile);
        mAdapter.notifyItemInserted(mMediaFileList.size());
    }
}
