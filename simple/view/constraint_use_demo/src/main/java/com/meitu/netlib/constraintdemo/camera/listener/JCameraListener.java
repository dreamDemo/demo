package com.meitu.netlib.constraintdemo.camera.listener;

import android.graphics.Bitmap;

/**
 * create by sunyuxin
 */
public interface JCameraListener {

    void captureSuccess(Bitmap bitmap);

    void recordSuccess(String url, Bitmap firstFrame);

}
