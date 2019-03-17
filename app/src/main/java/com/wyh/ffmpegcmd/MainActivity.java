package com.wyh.ffmpegcmd;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.wyh.ffmpegcmd.edit.audio.AudioFragment;
import com.wyh.ffmpegcmd.edit.video.VideoFragment;
import com.wyh.ffmpegcmd.ffmpeg.Callback;
import com.wyh.ffmpegcmd.ffmpeg.FFmpegAudio;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {


    private Fragment mAudioFragment;
    private Fragment mVideoFragment;
    private Fragment mAboutFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationBar mBottomNavigationBar = findViewById(R.id.bottomNavigationBar);
        mBottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_audiotrack_black_24dp, "audio"))
                .addItem(new BottomNavigationItem(R.drawable.ic_ondemand_video_black_24dp, "video"))
                .addItem(new BottomNavigationItem(R.drawable.ic_person_outline_black_24dp, "about"))
                .initialise();
        mBottomNavigationBar.setTabSelectedListener(this);

        if (savedInstanceState != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            mAudioFragment = fragmentManager.findFragmentByTag(AudioFragment.TAG);
            mVideoFragment = fragmentManager.findFragmentByTag(VideoFragment.TAG);
            mAboutFragment = fragmentManager.findFragmentByTag(AboutFragment.TAG);
        }

        if (mAudioFragment == null) {
            mAudioFragment = AudioFragment.newInstance();
        }
        if (mVideoFragment == null) {
            mVideoFragment = VideoFragment.newInstance();
        }
        if (mAboutFragment == null) {
            mAboutFragment = AudioFragment.newInstance();
        }

        changeFragment(0);

        requestWritePermissions();
    }

    private void requestWritePermissions() {
        int hasWritePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWritePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 111);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 111:
                break;
        }
    }


    @Override
    public void onTabSelected(int position) {
        changeFragment(position);
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    private void changeFragment(int position) {
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        switch (position) {
            case 1:
                trans.replace(R.id.frameLayout, mVideoFragment, VideoFragment.TAG);
                break;
            case 2:
                trans.replace(R.id.frameLayout, mAboutFragment, AboutFragment.TAG);
                break;
            case 0:
            default:
                trans.replace(R.id.frameLayout, mAudioFragment, AudioFragment.TAG);
                break;
        }
        trans.commitAllowingStateLoss();
    }
}
