package com.wyh.ffmpegcmd.edit.video;

import android.view.Menu;

import com.wyh.ffmpegcmd.edit.EditMediaListActivity;
import com.wyh.ffmpegcmd.ffmpeg.Callback;
import com.wyh.ffmpegcmd.ffmpeg.FFmpegVideo;
import com.wyh.ffmpegcmd.util.FileUtil;
import com.wyh.ffmpegcmd.util.SnackBarUtil;

import java.io.File;

public class VideoFilterActivity extends EditMediaListActivity {
    private static final String TAG = "VideoFilterActivity";
    public static final String TITLE = "视频滤镜";

    @Override
    protected String getEditTitle() {
        return TITLE;
    }

    @Override
    protected void createOptionsMenu(Menu menu) {
        menu.add("选择视频");
        menu.add("删除视频");
        menu.add("黑白");
        menu.add("色彩变换");
        menu.add("暗角");
        menu.add("底片");
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
        } else if (order == 2) {
            run(FFmpegVideo.Filter.filter_1);
        } else if (order == 3) {
            run(FFmpegVideo.Filter.filter_2);
        } else if (order == 4) {
            run(FFmpegVideo.Filter.filter_3);
        } else if (order == 5) {
            run(FFmpegVideo.Filter.filter_4);
        } else if (order == 6) {
            run(FFmpegVideo.Filter.filter_4);
        } else if (order == 7) {
            run(FFmpegVideo.Filter.filter_4);
        } else if (order == 8) {
            run(FFmpegVideo.Filter.filter_4);
        } else if (order == 9) {
            run(FFmpegVideo.Filter.filter_4);
        } else if (order == 10) {
            run(FFmpegVideo.Filter.filter_4);
        } else if (order == 11) {
            run(FFmpegVideo.Filter.filter_4);
        } else if (order == 12) {
            run(FFmpegVideo.Filter.filter_4);
        } else if (order == 13) {
            run(FFmpegVideo.Filter.filter_4);
        } else if (order == 14) {
            run(FFmpegVideo.Filter.filter_4);
        } else if (order == 15) {
            run(FFmpegVideo.Filter.filter_4);
        } else if (order == 16) {
            run(FFmpegVideo.Filter.filter_4);
        } else if (order == 17) {
            run(FFmpegVideo.Filter.filter_4);
        } else if (order == 18) {
            run(FFmpegVideo.Filter.filter_4);
        } else if (order == 19) {
            run(FFmpegVideo.Filter.filter_4);
        } else if (order == 20) {
            run(FFmpegVideo.Filter.filter_4);
        }
    }

    private void run(@FFmpegVideo.Filter String filter) {
        showLoadingDialog();
        final String output = FileUtil.OUTPUT_VIDEO_DIR + File.separator +
                "filter_" + TAG + ".mp4";
        FFmpegVideo.filterVideo(mMediaFileList.get(0).getPath(), filter,
                output, new Callback() {
                    @Override
                    public void onSuccess() {
                        dismissLoadingDialog();
                        showSaveDoneAndPlayDialog(output, true);
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
