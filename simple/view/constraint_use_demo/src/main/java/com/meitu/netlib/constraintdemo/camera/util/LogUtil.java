package com.meitu.netlib.constraintdemo.camera.util;

import android.util.Log;


/**
 * create by sunyuxin
 */
public class LogUtil {

    private static final String DEFAULT_TAG = "CJT";

    public static void i(String tag, String msg) {
//        if (DEBUG)
            Log.i(tag, msg);
    }

    public static void v(String tag, String msg) {
            Log.v(tag, msg);
    }

    public static void d(String tag, String msg) {
            Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
            Log.e(tag, msg);
    }

    public static void i(String msg) {
        i(DEFAULT_TAG, msg);
    }

    public static void v(String msg) {
        v(DEFAULT_TAG, msg);
    }

    public static void d(String msg) {
        d(DEFAULT_TAG, msg);
    }

    public static void e(String msg) {
        e(DEFAULT_TAG, msg);
    }
}
