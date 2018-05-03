package com.meitu.netlib.constraintdemo.camera.callback;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.ImageView;

import com.meitu.netlib.constraintdemo.camera.listener.ErrorListener;
import com.meitu.netlib.constraintdemo.camera.util.AngleUtil;
import com.meitu.netlib.constraintdemo.camera.util.CameraParamUtil;
import com.meitu.netlib.constraintdemo.camera.util.CheckPermission;
import com.meitu.netlib.constraintdemo.camera.util.ScreenUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.graphics.Bitmap.createBitmap;

/**
 * create by sunyuxin
 */
public class CameraInterface implements Camera.PreviewCallback {

    private static final String TAG = "CJT";

    private volatile static CameraInterface mCameraInterface;

    public static void destroyCameraInterface() {
        if (mCameraInterface != null) {
            mCameraInterface = null;
        }
    }

    private Camera mCamera;
    private Camera.Parameters mParams;
    private boolean isPreviewing = false;

    private int SELECTED_CAMERA = -1;
    private int CAMERA_POST_POSITION = -1;
    private int CAMERA_FRONT_POSITION = -1;

    private SurfaceHolder mHolder = null;
    private float screenProp = -1.0f;

    private boolean isRecorder = false;
    private ErrorListener errorLisenter;

    private ImageView mSwitchView;
    private ImageView mFlashLamp;
    private ImageView mGallery;

    private int preview_width;
    private int preview_height;

    private int angle = 0;
    private int cameraAngle = 90;//摄像头角度   默认为90度
    private int rotation = 0;
    private byte[] firstFrame_data;

    public static final int TYPE_RECORDER = 0x090;
    public static final int TYPE_CAPTURE = 0x091;
    private int nowScaleRate = 0;
    private int recordScleRate = 0;

    //视频质量
    private SensorManager sm = null;

    //获取CameraInterface单例
    public static synchronized CameraInterface getInstance() {
        if (mCameraInterface == null)
            synchronized (CameraInterface.class) {
                if (mCameraInterface == null)
                    mCameraInterface = new CameraInterface();
            }
        return mCameraInterface;
    }

    public void setSwitchView(ImageView mSwitchView, ImageView mFlashLamp, ImageView mGallery) {
        this.mSwitchView = mSwitchView;
        this.mFlashLamp = mFlashLamp;
        this.mGallery = mGallery;
        if (mSwitchView != null) {
            cameraAngle = CameraParamUtil.getInstance().getCameraDisplayOrientation(mSwitchView.getContext(),
                    SELECTED_CAMERA);
        }
    }

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        public void onSensorChanged(SensorEvent event) {
            if (Sensor.TYPE_ACCELEROMETER != event.sensor.getType()) {
                return;
            }
            float[] values = event.values;
            angle = AngleUtil.getSensorAngle(values[0], values[1]);
            rotationAnimation();
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    //切换摄像头icon跟随手机角度进行旋转
    private void rotationAnimation() {
        if (mSwitchView == null) {
            return;
        }
        if (rotation != angle) {
            int start_rotaion = 0;
            int end_rotation = 0;
            switch (rotation) {
                case 0:
                    start_rotaion = 0;
                    switch (angle) {
                        case 90:
                            end_rotation = -90;
                            break;
                        case 270:
                            end_rotation = 90;
                            break;
                    }
                    break;
                case 90:
                    start_rotaion = -90;
                    switch (angle) {
                        case 0:
                            end_rotation = 0;
                            break;
                        case 180:
                            end_rotation = -180;
                            break;
                    }
                    break;
                case 180:
                    start_rotaion = 180;
                    switch (angle) {
                        case 90:
                            end_rotation = 270;
                            break;
                        case 270:
                            end_rotation = 90;
                            break;
                    }
                    break;
                case 270:
                    start_rotaion = 90;
                    switch (angle) {
                        case 0:
                            end_rotation = 0;
                            break;
                        case 180:
                            end_rotation = 180;
                            break;
                    }
                    break;
            }
            ObjectAnimator animC = ObjectAnimator.ofFloat(mSwitchView, "rotation", start_rotaion, end_rotation);
            ObjectAnimator animF = ObjectAnimator.ofFloat(mFlashLamp, "rotation", start_rotaion, end_rotation);
            ObjectAnimator animG = ObjectAnimator.ofFloat(mGallery, "rotation", start_rotaion, end_rotation);
            AnimatorSet set = new AnimatorSet();
            set.playTogether(animC, animF, animG);
            set.setDuration(500);
            set.start();
            rotation = angle;
        }
    }

    public void setZoom(float zoom, int type) {
        if (mCamera == null) {
            return;
        }
        if (mParams == null) {
            mParams = mCamera.getParameters();
        }
        if (!mParams.isZoomSupported() || !mParams.isSmoothZoomSupported()) {
            return;
        }
        switch (type) {
            case TYPE_RECORDER:
                //如果不是录制视频中，上滑不会缩放
                if (!isRecorder) {
                    return;
                }
                if (zoom >= 0) {
                    //每移动50个像素缩放一个级别
                    int scaleRate = (int) (zoom / 40);
                    if (scaleRate <= mParams.getMaxZoom() && scaleRate >= nowScaleRate && recordScleRate != scaleRate) {
                        mParams.setZoom(scaleRate);
                        mCamera.setParameters(mParams);
                        recordScleRate = scaleRate;
                    }
                }
                break;
            case TYPE_CAPTURE:
                if (isRecorder) {
                    return;
                }
                //每移动50个像素缩放一个级别
                int scaleRate = (int) (zoom / 50);
                if (scaleRate < mParams.getMaxZoom()) {
                    nowScaleRate += scaleRate;
                    if (nowScaleRate < 0) {
                        nowScaleRate = 0;
                    } else if (nowScaleRate > mParams.getMaxZoom()) {
                        nowScaleRate = mParams.getMaxZoom();
                    }
                    mParams.setZoom(nowScaleRate);
                    mCamera.setParameters(mParams);
                }
                break;
        }

    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        firstFrame_data = data;
    }

    public void setFlashMode(String flashMode) {
        if (mCamera == null)
            return;
        Camera.Parameters params = mCamera.getParameters();
        params.setFlashMode(flashMode);
        mCamera.setParameters(params);
    }


    public interface CameraOpenOverCallback {
        void cameraHasOpened();
    }

    private CameraInterface() {
        findAvailableCameras();
        SELECTED_CAMERA = CAMERA_POST_POSITION;
    }


    /**
     * open Camera
     */
    public void doOpenCamera(CameraOpenOverCallback callback) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (!CheckPermission.isCameraUseable(SELECTED_CAMERA) && this.errorLisenter != null) {
                this.errorLisenter.onError();
                return;
            }
        }
        if (mCamera == null) {
            openCamera(SELECTED_CAMERA);
        }
        callback.cameraHasOpened();
    }

    private synchronized void openCamera(int id) {
        try {
            this.mCamera = Camera.open(id);
        } catch (Exception var3) {
            var3.printStackTrace();
            if (this.errorLisenter != null) {
                this.errorLisenter.onError();
            }
        }

        if (Build.VERSION.SDK_INT > 17 && this.mCamera != null) {
            try {
                this.mCamera.enableShutterSound(false);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("CJT", "enable shutter sound faild");
            }
        }
    }

    public synchronized void switchCamera(SurfaceHolder holder, float screenProp) {
        if (SELECTED_CAMERA == CAMERA_POST_POSITION) {
            SELECTED_CAMERA = CAMERA_FRONT_POSITION;
        } else {
            SELECTED_CAMERA = CAMERA_POST_POSITION;
        }
        doDestroyCamera();
        openCamera(SELECTED_CAMERA);
//        mCamera = Camera.open();
        if (Build.VERSION.SDK_INT > 17 && this.mCamera != null) {
            try {
                this.mCamera.enableShutterSound(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        doStartPreview(holder, screenProp);
    }

    /**
     * doStartPreview
     */
    public void doStartPreview(SurfaceHolder holder, float screenProp) {
        if (this.screenProp < 0) {
            this.screenProp = screenProp;
        }
        if (holder == null) {
            return;
        }
        this.mHolder = holder;
        if (mCamera != null) {
            try {
                mParams = mCamera.getParameters();
                Camera.Size previewSize = CameraParamUtil.getInstance().getPreviewSize(mParams
                        .getSupportedPreviewSizes(), 1000, screenProp);
                Camera.Size pictureSize = CameraParamUtil.getInstance().getPictureSize(mParams
                        .getSupportedPictureSizes(), 1200, screenProp);

                mParams.setPreviewSize(previewSize.width, previewSize.height);

                preview_width = previewSize.width;
                preview_height = previewSize.height;

                mParams.setPictureSize(pictureSize.width, pictureSize.height);

                if (CameraParamUtil.getInstance().isSupportedFocusMode(
                        mParams.getSupportedFocusModes(),
                        Camera.Parameters.FOCUS_MODE_AUTO)) {
                    mParams.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                }
                if (CameraParamUtil.getInstance().isSupportedPictureFormats(mParams.getSupportedPictureFormats(),
                        ImageFormat.JPEG)) {
                    mParams.setPictureFormat(ImageFormat.JPEG);
                    mParams.setJpegQuality(100);
                }
                mCamera.setParameters(mParams);
                mParams = mCamera.getParameters();
                mCamera.setPreviewDisplay(holder);  //SurfaceView
                mCamera.setDisplayOrientation(cameraAngle);//浏览角度
                mCamera.setPreviewCallback(this); //每一帧回调
                mCamera.startPreview();//启动浏览
                isPreviewing = true;
                Log.i(TAG, "=== Start Preview ===");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 停止预览
     */
    public void doStopPreview() {
        if (null != mCamera) {
            try {
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();
                //这句要在stopPreview后执行，不然会卡顿或者花屏
                mCamera.setPreviewDisplay(null);
                isPreviewing = false;
                Log.i(TAG, "=== Stop Preview ===");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 销毁Camera
     */
    public void doDestroyCamera() {
        errorLisenter = null;
        if (null != mCamera) {
            try {
                mCamera.setPreviewCallback(null);
                mSwitchView = null;
                mFlashLamp = null;
                mCamera.stopPreview();
                //这句要在stopPreview后执行，不然会卡顿或者花屏
                mCamera.setPreviewDisplay(null);
                mHolder = null;
                isPreviewing = false;
                mCamera.release();
                mCamera = null;
//                destroyCameraInterface();
                Log.i(TAG, "=== Destroy Camera ===");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "=== Camera  Null===");
        }
    }

    /**
     * 拍照
     */
    private int nowAngle;

    public void takePicture(final TakePictureCallback callback) {
        if (mCamera == null) {
            return;
        }
        switch (cameraAngle) {
            case 90:
                nowAngle = Math.abs(angle + cameraAngle) % 360;
                break;
            case 270:
                nowAngle = Math.abs(cameraAngle - angle);
                break;
        }
//
        Log.i("CJT", angle + " = " + cameraAngle + " = " + nowAngle);
        mCamera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                Matrix matrix = new Matrix();
                if (SELECTED_CAMERA == CAMERA_POST_POSITION) {
                    matrix.setRotate(nowAngle);
                } else if (SELECTED_CAMERA == CAMERA_FRONT_POSITION) {
                    matrix.setRotate(360 - nowAngle);
                    matrix.postScale(-1, 1);
                }

                bitmap = createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                if (callback != null) {
                    if (nowAngle == 90 || nowAngle == 270) {
                        callback.captureResult(bitmap, true);
                    } else {
                        callback.captureResult(bitmap, false);
                    }
                }
            }
        });
    }

    private void findAvailableCameras() {
        Camera.CameraInfo info = new Camera.CameraInfo();
        int cameraNum = Camera.getNumberOfCameras();
        for (int i = 0; i < cameraNum; i++) {
            Camera.getCameraInfo(i, info);
            switch (info.facing) {
                case Camera.CameraInfo.CAMERA_FACING_FRONT:
                    CAMERA_FRONT_POSITION = info.facing;
                    break;
                case Camera.CameraInfo.CAMERA_FACING_BACK:
                    CAMERA_POST_POSITION = info.facing;
                    break;
            }
        }
    }

    int handlerTime = 0;

    public void handleFocus(final Context context, final float x, final float y, final FocusCallback callback) {
        if (mCamera == null) {
            return;
        }
        final Camera.Parameters params = mCamera.getParameters();
        Rect focusRect = calculateTapArea(x, y, 1f, context);
        mCamera.cancelAutoFocus();
        if (params.getMaxNumFocusAreas() > 0) {
            List<Camera.Area> focusAreas = new ArrayList<>();
            focusAreas.add(new Camera.Area(focusRect, 800));
            params.setFocusAreas(focusAreas);
        } else {
            Log.i(TAG, "focus areas not supported");
            callback.focusSuccess();
            return;
        }
        final String currentFocusMode = params.getFocusMode();
        try {
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            mCamera.setParameters(params);
            mCamera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                    if (success || handlerTime > 10) {
                        Camera.Parameters params = camera.getParameters();
                        params.setFocusMode(currentFocusMode);
                        camera.setParameters(params);
                        handlerTime = 0;
                        callback.focusSuccess();
                    } else {
                        handlerTime++;
                        handleFocus(context, x, y, callback);
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "autoFocus failer");
        }
    }


    private static Rect calculateTapArea(float x, float y, float coefficient, Context context) {
        float focusAreaSize = 300;
        int areaSize = Float.valueOf(focusAreaSize * coefficient).intValue();
        int centerX = (int) (x / ScreenUtils.getScreenWidth(context) * 2000 - 1000);
        int centerY = (int) (y / ScreenUtils.getScreenHeight(context) * 2000 - 1000);
        int left = clamp(centerX - areaSize / 2, -1000, 1000);
        int top = clamp(centerY - areaSize / 2, -1000, 1000);
        RectF rectF = new RectF(left, top, left + areaSize, top + areaSize);
        return new Rect(Math.round(rectF.left), Math.round(rectF.top), Math.round(rectF.right), Math.round(rectF
                .bottom));
    }

    private static int clamp(int x, int min, int max) {
        if (x > max) {
            return max;
        }
        if (x < min) {
            return min;
        }
        return x;
    }

    public void setErrorLinsenter(ErrorListener errorLisenter) {
        this.errorLisenter = errorLisenter;
    }


    public interface StopRecordCallback {
        void recordResult(String url, Bitmap firstFrame);
    }

    interface ErrorCallback {
        void onError();
    }

    public interface TakePictureCallback {
        void captureResult(Bitmap bitmap, boolean isVertical);
    }

    public interface FocusCallback {
        void focusSuccess();

    }


    public void registerSensorManager(Context context) {
        if (sm == null) {
            sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        }
        sm.registerListener(sensorEventListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager
                .SENSOR_DELAY_NORMAL);
    }

    public void unregisterSensorManager(Context context) {
        if (sm == null) {
            sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        }
        sm.unregisterListener(sensorEventListener);
    }

    public void isPreview(boolean res) {
        this.isPreviewing = res;
    }
}
