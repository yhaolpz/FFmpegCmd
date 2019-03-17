package com.wyh.ffmpegcmd;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.wyh.ffmpegcmd.ffmpeg.Callback;
import com.wyh.ffmpegcmd.ffmpeg.FFmpegAudio;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void test(View view) {
        String dir = Environment.getExternalStorageDirectory().getPath() + "/audio/";
        String srcAudio = dir + "src.mp3";
        String audio1 = dir + "test.mp3";
        String outputAudio = dir + "output.mp3";
        List<String> audioPaths = new ArrayList<>();
        audioPaths.add(audio1);

        FFmpegAudio.mixAudio(srcAudio, audioPaths, outputAudio, new Callback() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this, "onSuccess", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFail() {
                Toast.makeText(MainActivity.this, "onFail", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
