package com.wyh.ffmpegcmd.edit.audio;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wyh.ffmpegcmd.R;
import com.wyh.ffmpegcmd.common.Click;
import com.wyh.ffmpegcmd.edit.EditItem;
import com.wyh.ffmpegcmd.edit.EditItemAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyh on 2019/3/17.
 */
public class AudioFragment extends Fragment {
    public static final String TAG = "AudioFragment";
    public static final String TITLE = "Audio";

    private RecyclerView mRecycleView;
    private EditItemAdapter mAdapter;
    private List<EditItem> mEditItemList;


    public static AudioFragment newInstance() {
        Bundle args = new Bundle();
        AudioFragment fragment = new AudioFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEditItemList = new ArrayList<>();
        mEditItemList.add(new EditItem(R.drawable.ic_trans_code_black_24dp,
                AudioTransCodeActivity.TITLE, AudioTransCodeActivity.class));
        mEditItemList.add(new EditItem(R.drawable.ic_call_merge_audio_24dp,
                AudioMixActivity.TITLE, AudioMixActivity.class));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecycleView = view.findViewById(R.id.recycleView);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 3);
        mRecycleView.setLayoutManager(manager);
        mAdapter = new EditItemAdapter(mEditItemList);
        mRecycleView.setAdapter(mAdapter);
        mAdapter.setEditItemOnClickListener(new Click.OnObjectClickListener<EditItem>() {
            @Override
            public void onObjectClick(EditItem editItem) {
                Intent intent = new Intent(getContext(), editItem.activityClass);
                if (getContext() != null) {
                    getContext().startActivity(intent);
                }
            }
        });
    }
}
