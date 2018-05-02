package com.meitu.netlib.constraintdemo.camera.state;

import android.view.SurfaceHolder;

import com.meitu.netlib.constraintdemo.camera.callback.CameraInterface;


/**
 * Created by sunyuxin on 2018/5/2.
 */
public interface State {

    void start(SurfaceHolder holder, float screenProp);

    void stop();

    void foucs(float x, float y, CameraInterface.FocusCallback callback);

    void swtich(SurfaceHolder holder, float screenProp);

    void restart();

    void capture();

    void cancle(SurfaceHolder holder, float screenProp);

    void confirm();

    void zoom(float zoom, int type);

    void flash(String mode);
}
