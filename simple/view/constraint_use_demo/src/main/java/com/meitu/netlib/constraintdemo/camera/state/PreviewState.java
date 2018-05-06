package com.meitu.netlib.constraintdemo.camera.state;

import android.graphics.Bitmap;
import android.view.SurfaceHolder;

import com.meitu.netlib.constraintdemo.camera.manager.CameraManager;


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
        CameraManager.getInstance().doStartPreview(holder, screenProp);
    }

    @Override
    public void stop() {
        CameraManager.getInstance().doStopPreview();
    }


    @Override
    public void foucs(float x, float y, CameraManager.FocusCallback callback) {
        if (CameraManager.getInstance().canFocus()) {
            if (machine.getView().handlerFoucs(x, y)) {
                CameraManager.getInstance().handleFocus(x, y, callback);
            }
        }
    }

    @Override
    public void swtich(SurfaceHolder holder, float screenProp) {
        CameraManager.getInstance().switchCamera(holder, screenProp);
    }

    @Override
    public void restart() {

    }

    @Override
    public void capture() {
        CameraManager.getInstance().takePicture(new CameraManager.TakePictureCallback() {
            @Override
            public void captureResult(Bitmap bitmap, boolean isVertical) {
                machine.getView().showPicture(bitmap, isVertical);
                machine.setState(machine.getBorrowPictureState());
                machine.confirm();
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
        CameraManager.getInstance().setZoom(zoom, type);
    }

    @Override
    public void flash(String mode) {
        CameraManager.getInstance().setFlashMode(mode);
    }
}
