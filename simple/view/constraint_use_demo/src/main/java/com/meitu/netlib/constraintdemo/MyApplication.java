package com.meitu.netlib.constraintdemo;

import android.app.Application;

/**
 * Created by sunyuxin on 2018/5/3.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BasicConfig.init(this);
    }
}
