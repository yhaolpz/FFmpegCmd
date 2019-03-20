package com.wyh.ffmpegcmd.edit.video;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import com.wyh.ffmpegcmd.R;
import com.wyh.ffmpegcmd.edit.BaseEditActivity;

public class VideoUnLogoActivity extends BaseEditActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_un_logo);
    }

    @Override
    protected String getEditTitle() {
        return null;
    }

    @Override
    protected void createOptionsMenu(Menu menu) {

    }

    @Override
    protected void onMenuClick(int order) {

    }
}
