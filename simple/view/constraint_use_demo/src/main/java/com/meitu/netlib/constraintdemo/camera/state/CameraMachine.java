package com.meitu.netlib.constraintdemo.camera.state;

import android.content.Context;
import android.view.SurfaceHolder;

import com.meitu.netlib.constraintdemo.camera.callback.CameraInterface;
import com.meitu.netlib.constraintdemo.camera.callback.CameraView;


/**
 * Created by sunyuxin on 2018/5/2.
 */
public class CameraMachine implements State {


    private Context context;
    private State state;
    private CameraView view;
//    private CameraInterface.CameraOpenOverCallback cameraOpenOverCallback;

    private State previewState;       //浏览状态(空闲)
    private State borrowPictureState; //浏览图片

    public CameraMachine(Context context, CameraView view, CameraInterface.CameraOpenOverCallback
            cameraOpenOverCallback) {
        this.context = context;
        previewState = new PreviewState(this);
        borrowPictureState = new BorrowPictureState(this);
        //默认设置为空闲状态
        this.state = previewState;
//        this.cameraOpenOverCallback = cameraOpenOverCallback;
        this.view = view;
    }

    public CameraView getView() {
        return view;
    }

    public Context getContext() {
        return context;
    }

    public void setState(State state) {
        this.state = state;
    }

    //获取浏览图片状态
    State getBorrowPictureState() {
        return borrowPictureState;
    }

    //获取空闲状态
    State getPreviewState() {
        return previewState;
    }

    @Override
    public void start(SurfaceHolder holder, float screenProp) {
        state.start(holder, screenProp);
    }

    @Override
    public void stop() {
        state.stop();
    }

    @Override
    public void foucs(float x, float y, CameraInterface.FocusCallback callback) {
        state.foucs(x, y, callback);
    }

    @Override
    public void swtich(SurfaceHolder holder, float screenProp) {
        state.swtich(holder, screenProp);
    }

    @Override
    public void restart() {
        state.restart();
    }

    @Override
    public void capture() {
        state.capture();
    }

    @Override
    public void cancle(SurfaceHolder holder, float screenProp) {
        state.cancle(holder, screenProp);
    }

    @Override
    public void confirm() {
        state.confirm();
    }


    @Override
    public void zoom(float zoom, int type) {
        state.zoom(zoom, type);
    }

    @Override
    public void flash(String mode) {
        state.flash(mode);
    }

    public State getState() {
        return this.state;
    }
}
