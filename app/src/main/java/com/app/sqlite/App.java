package com.app.sqlite;

import android.app.Application;

import com.coder.zzq.smartshow.core.SmartShow;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SmartShow.init(this);
    }
}
