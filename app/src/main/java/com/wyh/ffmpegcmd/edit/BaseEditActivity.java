package com.wyh.ffmpegcmd.edit;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.wyh.ffmpegcmd.BuildConfig;
import com.wyh.ffmpegcmd.R;
import com.wyh.ffmpegcmd.common.App;
import com.wyh.ffmpegcmd.common.SaveProgressDialog;
import com.wyh.ffmpegcmd.common.SecureAlertDialog;
import com.wyh.ffmpegcmd.common.SecureProgressDialog;
import com.wyh.ffmpegcmd.common.VideoUtil;
import com.wyh.ffmpegcmd.util.FileUtil;
import com.wyh.ffmpegcmd.util.SnackBarUtil;

import java.io.File;

/**
 * Created by wyh on 2019/3/17.
 */
public abstract class BaseEditActivity extends AppCompatActivity {

    private SecureProgressDialog mProgressDialog;
    private SaveProgressDialog mSaveProgressDialog;
    private static final int REQUEST_CODE_Permission = 1;
    protected static final int REQUEST_CODE_PICK_AUDIO = MediaFile.TYPE_AUDIO;
    protected static final int REQUEST_CODE_PICK_VIDEO = MediaFile.TYPE_VIDEO;
    protected static final int REQUEST_CODE_PICK_IMG = MediaFile.TYPE_IMG;

    protected Toolbar mToolbar;
    protected View mRoot;

    private Boolean mShowSaveDoneAndPlayDialog;
    private String mOutputPath;
    private boolean mIsVideo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mRoot = findViewById(R.id.root);
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle(getEditTitle());
        mToolbar.setOverflowIcon(getDrawable(R.drawable.ic_more_horiz_white_24dp));
        setSupportActionBar(mToolbar);
        mToolbar.inflateMenu(R.menu.menu_toolbar);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Menu menu = mToolbar.getMenu();
                for (int i = 0; i < menu.size(); i++) {
                    if (menu.getItem(i) == menuItem) {
                        onMenuClick(i);
                    }
                }
                return false;
            }
        });
        requestWritePermissions();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        menu.clear();
        createOptionsMenu(menu);
        return true;
    }

    protected abstract String getEditTitle();

    protected abstract void createOptionsMenu(Menu menu);

    protected abstract void onMenuClick(int order);

    protected void pickAudio() {
        pickMedia("audio/mp3", REQUEST_CODE_PICK_AUDIO);
    }

    protected void pickVideo() {
        pickMedia("video/*", REQUEST_CODE_PICK_VIDEO);
    }

    protected void pickImg() {
        pickMedia("image/*", REQUEST_CODE_PICK_IMG);
    }


    protected void onPickFile(@NonNull MediaFile mediaFile) {

    }

    private boolean isPickFileCode(int code) {
        return code == REQUEST_CODE_PICK_VIDEO || code == REQUEST_CODE_PICK_AUDIO ||
                code == REQUEST_CODE_PICK_IMG;
    }


    private void pickMedia(final String filter, int code) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(filter);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (isPickFileCode(requestCode) && resultCode == RESULT_OK) {
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
            }
            if (uri != null) {
                String path = FileUtil.getPath(this, uri);
                if (path != null) {
                    File file = new File(path);
                    if (file.exists()) {
                        String filePath = file.getPath();
                        String fileName = file.getName();
                        MediaFile mediaFile = new MediaFile();
                        mediaFile.setName(fileName);
                        mediaFile.setPath(filePath);
                        mediaFile.setType(requestCode);
                        onPickFile(mediaFile);
                    }
                }
            }
        }
    }

    protected boolean hasWritePermissions() {
        int hasWritePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return hasWritePermission == PackageManager.PERMISSION_GRANTED;
    }

    private void requestWritePermissions() {
        if (hasWritePermissions()) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_Permission);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_Permission:
                if (!hasWritePermissions()) {
                    Toast.makeText(this, "请开启文件读写权限", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mShowSaveDoneAndPlayDialog != null) {
            if (mShowSaveDoneAndPlayDialog) {
                if (!TextUtils.isEmpty(mOutputPath)) {
                    showSaveDoneAndPlayDialog(mOutputPath, mIsVideo);
                }
            }
        }
    }

    protected void showSaveDoneAndPlayDialog(final String outputAudio, final boolean video) {
        if (isFinishing() || isDestroyed()) {
            return;
        }
        VideoUtil.scanFile(outputAudio);
        final SecureAlertDialog alertDialog = new SecureAlertDialog(this);
        alertDialog.setTitle("成功");
        alertDialog.setMessage("已保存至：" + outputAudio);
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,
                "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog1, int which) {
                        dialog1.dismiss();
                        mShowSaveDoneAndPlayDialog = false;
                    }
                });
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                "播放", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog1, int which) {
                        dialog1.dismiss();
                        mShowSaveDoneAndPlayDialog = true;
                        mIsVideo = video;
                        mOutputPath = outputAudio;
                        if (video) {
                            playVideo(outputAudio);
                        } else {
                            playAudio(outputAudio);
                        }
                    }
                });
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
                alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.RED);
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.create();
        alertDialog.show();
    }


    protected void playAudio(String path) {
        play(path, "audio/*");
    }

    protected void playVideo(String path) {
        play(path, "video/*");
    }

    protected void play(String path, String filter) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        File file = new File(path);
        Uri contentUri = FileProvider.getUriForFile(App.get(), BuildConfig.APPLICATION_ID + ".fileProvider", file);
        intent.setDataAndType(contentUri, filter);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivity(intent);
    }

    /**
     * 设置正在加载框文字
     */
    @MainThread
    protected void setLoadingText(String text) {
        if (isFinishing() || isDestroyed()) {
            return;
        }
        initLoadingDialog();
        mProgressDialog.setMessage(text);
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    /**
     * 展示正在加载框
     */
    @MainThread
    protected void showLoadingDialog() {
        if (isFinishing() || isDestroyed()) {
            return;
        }
        initLoadingDialog();
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    /**
     * 隐藏正在加载框
     */
    @MainThread
    protected void dismissLoadingDialog() {
        if (isFinishing() || isDestroyed()) {
            return;
        }
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * 初始化正在加载框
     */
    @MainThread
    private void initLoadingDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new SecureProgressDialog(this);
            mProgressDialog.setMessage("正在处理...");
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
    }

    protected void showProgressDialog(int progress) {
        if (mSaveProgressDialog == null) {
            mSaveProgressDialog = SaveProgressDialog.getInstance(this);
        }
        if (!mSaveProgressDialog.isShowing()) {
            mSaveProgressDialog.show();
        }
        mSaveProgressDialog.updateProgress(progress);
    }

    protected void dismissProgressDialog() {
        if (mSaveProgressDialog != null) {
            mSaveProgressDialog.dismiss();
            mSaveProgressDialog = null;
        }
    }

    protected void showErrorSnack(String msg) {
        SnackBarUtil.showError(mRoot, msg);
    }

    protected void showInfoSnack(String msg) {
        SnackBarUtil.showInfo(mRoot, msg);
    }

}
