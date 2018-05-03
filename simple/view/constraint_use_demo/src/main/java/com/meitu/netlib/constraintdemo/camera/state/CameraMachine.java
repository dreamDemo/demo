package com.meitu.netlib.constraintdemo.camera.state;

import android.content.Context;
import android.view.SurfaceHolder;

import com.meitu.netlib.constraintdemo.camera.callback.CameraInterface;
import com.meitu.netlib.constraintdemo.camera.callback.CameraViewInterface;


/**
 * Created by sunyuxin on 2018/5/2.
 */
public class CameraMachine implements State {


    private Context context;
    private State state;
    private CameraViewInterface view;

    private State previewState;       //预览状态
    private State borrowPictureState; //查看图片状态

    public CameraMachine(Context context, CameraViewInterface view, CameraInterface.CameraOpenOverCallback
            cameraOpenOverCallback) {
        this.context = context;
        previewState = new PreviewState(this);
        borrowPictureState = new BorrowPictureState(this);
        //默认设置为空闲状态
        this.state = previewState;
        this.view = view;
    }

    public CameraViewInterface getView() {
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

    //预览状态
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
