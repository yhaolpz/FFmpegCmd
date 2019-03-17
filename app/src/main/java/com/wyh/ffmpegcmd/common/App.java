package com.wyh.ffmpegcmd.common;

import android.app.Application;

/**
 * Created by wyh on 2019/3/13.
 */
public class App extends Application {

    private static App app;

    public static App get() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
}
