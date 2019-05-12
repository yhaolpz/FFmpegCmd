package com.wyh.ffmpegcmd.common;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wyh.ffmpegcmd.R;

import java.util.Locale;

/**
 * 带有进度条的dialog
 */
public class SaveProgressDialog extends SecureDialog implements View.OnClickListener {

    public static final String TAG = "SaveProgressDialog";

    private static final String PROGRESS = "%d%%";

    private ProgressBar mProgressBar;
    private TextView mTvProgress;
    private SavingDialogCallback mSavingDialogCallback;

    public SaveProgressDialog(@NonNull Context context) {
        super(context);
    }

    public SaveProgressDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public interface SavingDialogCallback {
        void cancelVideoSave();
    }

    public void setSavingDialogCallback(SavingDialogCallback callback) {
        this.mSavingDialogCallback = callback;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressBar = findViewById(R.id.download_progress_view);
        mTvProgress = findViewById(R.id.tv_progress_text);
        findViewById(R.id.btn_cancel).setVisibility(View.GONE);
        setCancelable(false);
    }

    public static SaveProgressDialog getInstance(Context context) {
        SaveProgressDialog dialog = new SaveProgressDialog(context, R.style.CommonDialog);
        dialog.setContentView(R.layout.save_progress);
        return dialog;
    }


    public void updateProgress(@IntRange(from = 0, to = 100) int progress) {
        if (null != mTvProgress && null != mProgressBar) {
            if (progress != mProgressBar.getProgress()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    mProgressBar.setProgress(progress, true);
                } else {
                    mProgressBar.setProgress(progress);
                }
                mTvProgress.setText(String.format(Locale.getDefault(), PROGRESS, progress));
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_cancel) { // 取消保存
            if (null != mSavingDialogCallback) {
                mSavingDialogCallback.cancelVideoSave();
            }
            dismiss();
        }
    }

}
