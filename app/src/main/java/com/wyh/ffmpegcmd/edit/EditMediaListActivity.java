package com.wyh.ffmpegcmd.edit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wyh.ffmpegcmd.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyh on 2019/3/20.
 */
public abstract class EditMediaListActivity extends BaseEditActivity {


    protected RecyclerView mRecyclerView;
    protected ItemMediaAdapter mAdapter;
    protected List<MediaFile> mMediaFileList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_list);
        mRecyclerView = findViewById(R.id.recycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMediaFileList = new ArrayList<>();
        mAdapter = new ItemMediaAdapter(mMediaFileList);
        mRecyclerView.setAdapter(mAdapter);
    }


    protected void deleteLastMediaFile() {
        if (mMediaFileList.size() < 1) {
            return;
        }
        mMediaFileList.remove(mMediaFileList.size() - 1);
        mAdapter.notifyItemRemoved(mMediaFileList.size());
    }


    @Override
    protected void onPickFile(@NonNull MediaFile mediaFile) {
        super.onPickFile(mediaFile);
        mMediaFileList.add(mediaFile);
        mAdapter.notifyItemInserted(mMediaFileList.size());
    }
}
