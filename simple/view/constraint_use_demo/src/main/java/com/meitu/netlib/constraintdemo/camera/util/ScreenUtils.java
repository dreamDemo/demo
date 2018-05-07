package com.meitu.netlib.constraintdemo.camera.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.meitu.netlib.constraintdemo.BasicConfig;

/**
 * create by sunyuxin
 */
public class ScreenUtils {
    public static int getScreenHeight() {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager wm = (WindowManager) BasicConfig.getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;
    }

    public static int getScreenWidth() {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager wm = (WindowManager) BasicConfig.getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }
}
