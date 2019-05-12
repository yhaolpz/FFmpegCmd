package com.wyh.ffmpegcmd.edit.video;

import android.view.Menu;

import com.wyh.ffmpegcmd.common.AppExecutors;
import com.wyh.ffmpegcmd.common.Logger;
import com.wyh.ffmpegcmd.common.VideoUtil;
import com.wyh.ffmpegcmd.edit.EditMediaListActivity;
import com.wyh.ffmpegcmd.edit.MediaFile;
import com.wyh.ffmpegcmd.ffmpeg.Callback;
import com.wyh.ffmpegcmd.ffmpeg.FFmpegApi;
import com.wyh.ffmpegcmd.ffmpeg.FFmpegVideo;
import com.wyh.ffmpegcmd.util.FileUtil;
import com.wyh.ffmpegcmd.util.SnackBarUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VideoPuzzActivity extends EditMediaListActivity {
    private static final String TAG = "VideoPuzzActivity";

    public static final String TITLE = "视频拼图";


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
            deleteLastMediaFile();
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


    private void run(List<MediaFile> mediaFileList) {
//        final long duration = getLongVideoDuration(mediaFileList);
        final long duration = -1;
        if (duration < 0) {
            showLoadingDialog();
        } else {
            showProgressDialog(1);
        }
        final String output = FileUtil.OUTPUT_VIDEO_DIR + File.separator +
                "mix_" + TAG + ".mp4";
        List<String> audioPaths = new ArrayList<>();
        for (int i = 0; i < mediaFileList.size(); i++) {
            audioPaths.add(mediaFileList.get(i).getPath());
        }
        FFmpegVideo.mixVideo(audioPaths, output, new Callback() {
            @Override
            public void onSuccess() {
                dismissLoadingDialog();
                dismissProgressDialog();
                showSaveDoneAndPlayDialog(output, true);
            }

            @Override
            public void onLog(String log) {
                if (duration < 0) {
                    return;
                }
                int index = log.indexOf(", dts ");
                if (index > 0) {
                    index += ", dts ".length();
                    String tsString = log.substring(index);
                    tsString = tsString.trim();
                    long ts = Long.parseLong(tsString);
                    final long ms = ts / 1000;
                    AppExecutors.executeMain(new Runnable() {
                        @Override
                        public void run() {
                            showProgressDialog((int) ((ms * 1.0f / duration) * 100));
                        }
                    });
                }
            }

            @Override
            public void onFail() {
                dismissLoadingDialog();
                dismissProgressDialog();
                SnackBarUtil.showError(mRoot, "合成失败");
            }
        });
    }


    private long getLongVideoDuration(List<MediaFile> mediaFileList) {
        long ms = -1;
        if (mediaFileList != null) {
            for (MediaFile mediaFile : mediaFileList) {
                String str = VideoUtil.getVideoDuration(mediaFile.getPath());
                ms = Math.max(ms, Integer.valueOf(str));
            }
        }
        return ms;
    }


}
