package com.meitu.netlib.constraintdemo.camera.callback;

import android.graphics.Bitmap;

/**
 * create by sunyuxin
 */
public interface CameraView {
    void resetState(int type);

    void confirmState(int type);

    void showPicture(Bitmap bitmap, boolean isVertical);

    boolean handlerFoucs(float x, float y);
}
