package com.wyh.ffmpegcmd.edit.video;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.wyh.ffmpegcmd.R;
import com.wyh.ffmpegcmd.edit.BaseEditActivity;

public class VideoPuzzActivity extends BaseEditActivity {

    public static final String TITLE = "视频拼图";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_puzz);
    }

    @Override
    protected String getEditTitle() {
        return TITLE;
    }

    @Override
    protected void onMenuClick(int order) {

    }

}
