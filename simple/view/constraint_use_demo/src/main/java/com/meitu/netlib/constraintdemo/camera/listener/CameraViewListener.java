package com.meitu.netlib.constraintdemo.camera.listener;

import android.graphics.Bitmap;

/**
 * create by sunyuxin
 */
public interface CameraViewListener {
    void resetState(int type);

    void confirmState(int type);

    void showPicture(Bitmap bitmap, boolean isVertical);

    boolean handlerFoucs(float x, float y);
}
