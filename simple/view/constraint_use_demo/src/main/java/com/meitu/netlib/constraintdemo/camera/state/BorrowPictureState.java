package com.meitu.netlib.constraintdemo.camera.state;

import android.util.Log;
import android.view.SurfaceHolder;

import com.meitu.netlib.constraintdemo.camera.callback.CameraInterface;
import com.meitu.netlib.constraintdemo.camera.view.CameraView;


/**
 * Created by sunyuxin on 2018/5/2.
 */
public class BorrowPictureState implements State {
    private final String TAG = "BorrowPictureState";
    private CameraMachine machine;

    public BorrowPictureState(CameraMachine machine) {
        this.machine = machine;
    }

    @Override
    public void start(SurfaceHolder holder, float screenProp) {
        CameraInterface.getInstance().doStartPreview(holder, screenProp);
        machine.setState(machine.getPreviewState());
    }

    @Override
    public void stop() {

    }


    @Override
    public void foucs(float x, float y, CameraInterface.FocusCallback callback) {
    }

    @Override
    public void swtich(SurfaceHolder holder, float screenProp) {

    }

    @Override
    public void restart() {

    }

    @Override
    public void capture() {

    }

    @Override
    public void cancle(SurfaceHolder holder, float screenProp) {
        CameraInterface.getInstance().doStartPreview(holder, screenProp);
        machine.getView().resetState(CameraView.TYPE_PICTURE);
        machine.setState(machine.getPreviewState());
    }

    @Override
    public void confirm() {
        machine.getView().confirmState(CameraView.TYPE_PICTURE);
        machine.setState(machine.getPreviewState());
    }

    @Override
    public void zoom(float zoom, int type) {
        Log.i(TAG, "zoom");
    }

    @Override
    public void flash(String mode) {

    }

}
