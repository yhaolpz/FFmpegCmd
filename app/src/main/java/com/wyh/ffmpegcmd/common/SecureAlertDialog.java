package com.wyh.ffmpegcmd.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


/**
 * 考虑多种Dialog异常
 */
public class SecureAlertDialog extends AlertDialog implements LifecycleObserver {
    private static final String TAG = "SecureAlertDialog";

    private Context mContext;


    public SecureAlertDialog(@NonNull Context context) {
        super(context);
        init(context);
    }

    public SecureAlertDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected SecureAlertDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
    }

    @Override
    public void show() {
        Activity activity = (Activity) mContext;
        if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
            Log.d(TAG, "show is invalid, activity is abnormal");
            return;
        }
        if (Looper.myLooper() == null || Looper.myLooper() != Looper.getMainLooper()) {
            Log.d(TAG, "show is invalid, no in UI thread");
            return;
        }
        if (activity instanceof AppCompatActivity) {
            ((AppCompatActivity) activity).getLifecycle().addObserver(this);
        }
        super.show();
    }


    @Override
    public void dismiss() {
        try {
            Activity activity = (Activity) mContext;

            if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
                return;
            }
            if (isShowing()) {
                super.dismiss();
            }
            if (activity instanceof AppCompatActivity) {
                ((AppCompatActivity) activity).getLifecycle().removeObserver(this);
            }
        } catch (Throwable e) {
            Log.w(TAG, e);
        }
    }

    @Override
    public void cancel() {
        try {
            Activity activity = (Activity) mContext;

            if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
                return;
            }
            if (isShowing()) {
                super.cancel();
            }
            if (activity instanceof AppCompatActivity) {
                ((AppCompatActivity) activity).getLifecycle().removeObserver(this);
            }
        } catch (Throwable e) {
            Log.w(TAG, e);
        }

    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy() {
        try {
            if (isShowing()) {
                super.dismiss();
            }
            Activity activity = (Activity) mContext;
            if (activity instanceof AppCompatActivity) {
                ((AppCompatActivity) activity).getLifecycle().removeObserver(this);
            }
        } catch (Throwable e) {
            Log.w(TAG, e);
        }
    }


}
