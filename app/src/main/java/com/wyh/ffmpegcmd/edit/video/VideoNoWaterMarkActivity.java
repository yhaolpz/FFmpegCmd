package com.wyh.ffmpegcmd.edit.video;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.wyh.ffmpegcmd.R;
import com.wyh.ffmpegcmd.common.SecureAlertDialog;
import com.wyh.ffmpegcmd.edit.BaseEditActivity;
import com.wyh.ffmpegcmd.edit.EditMediaListActivity;
import com.wyh.ffmpegcmd.edit.ItemMediaAdapter;
import com.wyh.ffmpegcmd.edit.MediaFile;
import com.wyh.ffmpegcmd.ffmpeg.Callback;
import com.wyh.ffmpegcmd.ffmpeg.FFmpegVideo;
import com.wyh.ffmpegcmd.util.DateUtil;
import com.wyh.ffmpegcmd.util.FileUtil;
import com.wyh.ffmpegcmd.util.KeyBoardUtil;
import com.wyh.ffmpegcmd.util.SnackBarUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VideoNoWaterMarkActivity extends EditMediaListActivity {
    private static final String TAG = "VideoNoWaterMarkActivity";
    public static final String TITLE = "去水印";


    @Override
    protected String getEditTitle() {
        return TITLE;
    }

    @Override
    protected void createOptionsMenu(Menu menu) {
        menu.add("选择视频");
        menu.add("删除");
        menu.add("开始");
    }

    @Override
    protected void onMenuClick(int order) {
        if (order == 0) {
            if (mMediaFileList.size() == 1) {
                SnackBarUtil.showError(mRoot, "已选择视频");
                return;
            }
            pickVideo();
        } else if (order == 1) {
            deleteLastMediaFile();
        } else {
            if (mMediaFileList.size() == 0) {
                SnackBarUtil.showError(mRoot, "请选择视频");
                return;
            }
            List<String> titles = new ArrayList<>();
            List<Integer> value = new ArrayList<>();
            titles.add("请输入水印x坐标");
            titles.add("请输入水印y坐标");
            titles.add("请输入水印宽度");
            titles.add("请输入水印高度");
            showInputDialog(titles, value);
        }
    }


    private void showInputDialog(final List<String> titles, final List<Integer> value) {
        final SecureAlertDialog alertDialog = new SecureAlertDialog(this);
        final TextInputLayout linearLayout = new TextInputLayout(this);
        final EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setFocusable(true);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT
                , LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setPadding(20, 20, 20, 20);
        linearLayout.addView(editText, params);
        alertDialog.setView(linearLayout);
        alertDialog.setTitle(titles.get(0));
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                "确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog1, int which) {
                        if (TextUtils.isEmpty(editText.getText())) {
                            showInputDialog(titles, value);
                            return;
                        }
                        alertDialog.dismiss();
                        value.add(Integer.parseInt(editText.getText().toString()));
                        titles.remove(0);
                        if (titles.size() > 0) {
                            showInputDialog(titles, value);
                        } else {
                            KeyBoardUtil.hideSoftKeyboard(editText);
                            run(value);
                        }
                    }
                });
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.RED);
                KeyBoardUtil.showKeybord(editText);
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.create();
        alertDialog.show();
    }

    private void run(List<Integer> value) {
        showLoadingDialog();
        final String output = FileUtil.OUTPUT_VIDEO_DIR + File.separator +
                "cleanWaterMark_" + DateUtil.format(new Date()) + ".mp4";
        FFmpegVideo.cleanWaterMark(mMediaFileList.get(0).getPath(), value.get(0),
                value.get(1), value.get(2), value.get(3), output, new Callback() {
                    @Override
                    public void onSuccess() {
                        dismissLoadingDialog();
                        KeyBoardUtil.hideSoftKeyboard(VideoNoWaterMarkActivity.this);
                        showSaveDoneAndPlayDialog(output, true);
                    }

                    @Override
                    public void onFail() {
                        dismissLoadingDialog();
                        KeyBoardUtil.hideSoftKeyboard(VideoNoWaterMarkActivity.this);
                    }
                }
        );
    }
}
