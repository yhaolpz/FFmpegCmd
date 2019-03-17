package com.wyh.ffmpegcmd;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
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
    private Fragment mCurrentFragment;

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
            mAboutFragment = AboutFragment.newInstance();
        }
        changeFragment(0);
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
        ActionBar bar = getSupportActionBar();
        switch (position) {
            case 1:
                switchFragment(mVideoFragment, VideoFragment.TAG);
                bar.setTitle(VideoFragment.TITLE);
                break;
            case 2:
                switchFragment(mAboutFragment, AboutFragment.TAG);
                bar.setTitle(AboutFragment.TITLE);
                break;
            case 0:
            default:
                switchFragment(mAudioFragment, AudioFragment.TAG);
                bar.setTitle(AudioFragment.TITLE);
                break;
        }
    }


    private void switchFragment(Fragment targetFragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!targetFragment.isAdded()) {
            if (mCurrentFragment != null) {
                transaction.hide(mCurrentFragment);
            }
            transaction.add(R.id.frameLayout, targetFragment, tag).commit();
        } else {
            if (mCurrentFragment != null) {
                transaction.hide(mCurrentFragment);
            }
            transaction.show(targetFragment).commit();
        }
        mCurrentFragment = targetFragment;
    }

}
