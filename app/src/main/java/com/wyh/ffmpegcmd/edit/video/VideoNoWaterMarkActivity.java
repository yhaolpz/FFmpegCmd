package com.wyh.ffmpegcmd.edit.video;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import com.wyh.ffmpegcmd.R;
import com.wyh.ffmpegcmd.edit.BaseEditActivity;

public class VideoNoWaterMarkActivity extends BaseEditActivity {
    private static final String TAG = "VideoNoWaterMarkActivity";
    public static final String TITLE = "去水印";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_no_water_mark);
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
