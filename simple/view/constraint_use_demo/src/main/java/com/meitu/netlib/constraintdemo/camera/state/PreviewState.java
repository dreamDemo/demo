package com.meitu.netlib.constraintdemo.camera.state;

import android.graphics.Bitmap;
import android.view.SurfaceHolder;

import com.meitu.netlib.constraintdemo.BasicConfig;
import com.meitu.netlib.constraintdemo.camera.callback.CameraInterface;


/**
 * Created by sunyuxin on 2018/5/2.
 */
class PreviewState implements State {
    public static final String TAG = "PreviewState";

    private CameraMachine machine;

    PreviewState(CameraMachine machine) {
        this.machine = machine;
    }

    @Override
    public void start(SurfaceHolder holder, float screenProp) {
        CameraInterface.getInstance().doStartPreview(holder, screenProp);
    }

    @Override
    public void stop() {
        CameraInterface.getInstance().doStopPreview();
    }


    @Override
    public void foucs(float x, float y, CameraInterface.FocusCallback callback) {
        if (machine.getView().handlerFoucs(x, y)) {
            CameraInterface.getInstance().handleFocus(x, y, callback);
        }
    }

    @Override
    public void swtich(SurfaceHolder holder, float screenProp) {
        CameraInterface.getInstance().switchCamera(holder, screenProp);
    }

    @Override
    public void restart() {

    }

    @Override
    public void capture() {
        CameraInterface.getInstance().takePicture(new CameraInterface.TakePictureCallback() {
            @Override
            public void captureResult(Bitmap bitmap, boolean isVertical) {
                machine.getView().showPicture(bitmap, isVertical);
                machine.setState(machine.getBorrowPictureState());
            }
        });
    }

    @Override
    public void cancle(SurfaceHolder holder, float screenProp) {
    }

    @Override
    public void confirm() {
    }

    @Override
    public void zoom(float zoom, int type) {
        CameraInterface.getInstance().setZoom(zoom, type);
    }

    @Override
    public void flash(String mode) {
        CameraInterface.getInstance().setFlashMode(mode);
    }
}
