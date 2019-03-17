package com.wyh.ffmpegcmd.edit.audio;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.wyh.ffmpegcmd.MainActivity;
import com.wyh.ffmpegcmd.ffmpeg.Callback;
import com.wyh.ffmpegcmd.ffmpeg.FFmpegAudio;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyh on 2019/3/17.
 */
public class AudioMixFragment extends Fragment {
    private static final String TAG = "AudioMixFragment";

    public static AudioMixFragment newInstance() {
        
        Bundle args = new Bundle();
        
        AudioMixFragment fragment = new AudioMixFragment();
        fragment.setArguments(args);
        return fragment;
    }



    public void test() {
        String dir = Environment.getExternalStorageDirectory().getPath() + "/audio/";
        String srcAudio = dir + "src.mp3";
        String audio1 = dir + "test.mp3";
        String outputAudio = dir + "output.mp3";
        List<String> audioPaths = new ArrayList<>();
        audioPaths.add(audio1);

        FFmpegAudio.mixAudio(srcAudio, audioPaths, outputAudio, new Callback() {
            @Override
            public void onSuccess() {
                Toast.makeText(getContext(), "onSuccess", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFail() {
                Toast.makeText(getContext(), "onFail", Toast.LENGTH_SHORT).show();

            }
        });

    }


}
