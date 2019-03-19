package com.wyh.ffmpegcmd.edit.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;

import com.wyh.ffmpegcmd.R;
import com.wyh.ffmpegcmd.edit.BaseEditActivity;

/**
 * Created by wyh on 2019/3/18.
 */
public class VideoWaterMarkActivity extends BaseEditActivity {
    private static final String TAG = "VideoWaterMarkActivity";
    public static final String TITLE = "加水印";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_mark);
    }



    @Override
    protected String getEditTitle() {
        return TITLE;
    }

    @Override
    protected void createOptionsMenu(Menu menu) {

    }

    @Override
    protected void onMenuClick(int order) {

    }
}
