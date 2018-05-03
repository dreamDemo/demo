package com.meitu.netlib.constraintdemo.camera.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.VideoView;

import com.meitu.netlib.constraintdemo.R;
import com.meitu.netlib.constraintdemo.camera.callback.CameraInterface;
import com.meitu.netlib.constraintdemo.camera.callback.CameraView;
import com.meitu.netlib.constraintdemo.camera.listener.CaptureListener;
import com.meitu.netlib.constraintdemo.camera.listener.ClickListener;
import com.meitu.netlib.constraintdemo.camera.listener.ErrorListener;
import com.meitu.netlib.constraintdemo.camera.listener.JCameraListener;
import com.meitu.netlib.constraintdemo.camera.listener.TypeListener;
import com.meitu.netlib.constraintdemo.camera.state.CameraMachine;
import com.meitu.netlib.constraintdemo.camera.util.ScreenUtils;


/**
 * create by sunyuxin
 */
public class Camera1View extends FrameLayout implements CameraInterface.CameraOpenOverCallback, SurfaceHolder
        .Callback, CameraView {

    //Camera状态机
    private CameraMachine machine;

    //闪关灯状态
    private static final int TYPE_FLASH_AUTO = 0x021;
    private static final int TYPE_FLASH_ON = 0x022;
    private static final int TYPE_FLASH_OFF = 0x023;
    private int type_flash = TYPE_FLASH_OFF;

    //拍照浏览时候的类型
    public static final int TYPE_PICTURE = 0x001;
    public static final int TYPE_DEFAULT = 0x004;


    public static final int BUTTON_STATE_ONLY_CAPTURE = 0x101;      //只能拍照


    //回调监听
    private JCameraListener jCameraLisenter;
    private ClickListener leftClickListener;
    private ClickListener rightClickListener;

    private Context mContext;
    private VideoView mVideoView;
    private ImageView mPhoto;
    private ImageView mSwitchCamera;
    private ImageView mClose;
    private ImageView mFlashLamp;
    private ImageView mGallery;
    private CaptureLayout mCaptureLayout;
    private FoucsView mFoucsView;

    private int layout_width;
    private float screenProp = 0f;

    private Bitmap captureBitmap;   //捕获的图片


    //切换摄像头按钮的参数
    private int iconSrc = 0;        //图标资源
    private int iconLeft = 0;       //左图标
    private int iconRight = 0;      //右图标

    //缩放梯度
    private int zoomGradient = 0;

    private boolean firstTouch = true;
    private float firstTouchLength = 0;

    public Camera1View(Context context) {
        this(context, null);
    }

    public Camera1View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Camera1View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        //get AttributeSet
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Camera1View, defStyleAttr, 0);
        iconSrc = a.getResourceId(R.styleable.Camera1View_iconSrc, R.drawable.ic_camera);
        iconLeft = a.getResourceId(R.styleable.Camera1View_iconLeft, 0);
        iconRight = a.getResourceId(R.styleable.Camera1View_iconRight, 0);
        a.recycle();
        initData();
        initView();
    }

    private void initData() {
        layout_width = ScreenUtils.getScreenWidth(mContext);
        //缩放梯度
        zoomGradient = (int) (layout_width / 16f);
        machine = new CameraMachine(getContext(), this, this);
    }

    private void initView() {
        setWillNotDraw(false);
        View view = LayoutInflater.from(mContext).inflate(R.layout.camera_view, this);
        mVideoView = (VideoView) view.findViewById(R.id.video_preview);
        mPhoto = (ImageView) view.findViewById(R.id.image_photo);
        mSwitchCamera = (ImageView) view.findViewById(R.id.image_switch);
        mClose = (ImageView) view.findViewById(R.id.image_close);
        mSwitchCamera.setImageResource(iconSrc);
        mFlashLamp = (ImageView) view.findViewById(R.id.image_flash);
        setFlashRes();
        mFlashLamp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                type_flash++;
                if (type_flash > 0x023)
                    type_flash = TYPE_FLASH_AUTO;
                setFlashRes();
            }
        });
        mCaptureLayout = (CaptureLayout) view.findViewById(R.id.capture_layout);
        mGallery = (ImageView) mCaptureLayout.findViewById(R.id.gallery);
        mCaptureLayout.setIconSrc(iconLeft, iconRight);
        mFoucsView = (FoucsView) view.findViewById(R.id.fouce_view);
        mVideoView.getHolder().addCallback(this);
        //切换摄像头
        mSwitchCamera.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                machine.swtich(mVideoView.getHolder(), screenProp);
            }
        });
        //拍照 录像
        mCaptureLayout.setCaptureLisenter(new CaptureListener() {
            @Override
            public void takePictures() {
                mSwitchCamera.setVisibility(GONE);
                mFlashLamp.setVisibility(INVISIBLE);
                machine.capture();
            }
        });
        //确认 取消
        mCaptureLayout.setTypeLisenter(new TypeListener() {
            @Override
            public void cancel() {
                machine.cancle(mVideoView.getHolder(), screenProp);
            }

            @Override
            public void confirm() {
                machine.confirm();
            }
        });
        mClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getContext() instanceof Activity) {
                    if (!((Activity) v.getContext()).isFinishing()) {
                        ((Activity) v.getContext()).finish();
                    }
                }
             }
        });
        mCaptureLayout.setLeftClickListener(new ClickListener() {
            @Override
            public void onClick() {
                if (leftClickListener != null) {
                    leftClickListener.onClick();
                }
            }
        });
        mCaptureLayout.setRightClickListener(new ClickListener() {
            @Override
            public void onClick() {
                if (rightClickListener != null) {
                    rightClickListener.onClick();
                }
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float widthSize = mVideoView.getMeasuredWidth();
        float heightSize = mVideoView.getMeasuredHeight();
        if (screenProp == 0) {
            screenProp = heightSize / widthSize;
        }
    }

    @Override
    public void cameraHasOpened() {
        CameraInterface.getInstance().doStartPreview(mVideoView.getHolder(), screenProp);
    }

    //生命周期onResume
    public void onResume() {
        resetState(TYPE_DEFAULT); //重置状态
        CameraInterface.getInstance().registerSensorManager(mContext);
        CameraInterface.getInstance().setSwitchView(mSwitchCamera, mFlashLamp, mGallery);
        machine.start(mVideoView.getHolder(), screenProp);
    }

    //生命周期onPause
    public void onPause() {
        resetState(TYPE_PICTURE);
        CameraInterface.getInstance().isPreview(false);
        CameraInterface.getInstance().unregisterSensorManager(mContext);
    }

    //SurfaceView生命周期
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        new Thread() {
            @Override
            public void run() {
                CameraInterface.getInstance().doOpenCamera(Camera1View.this);
            }
        }.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        CameraInterface.getInstance().doDestroyCamera();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getPointerCount() == 1) {
                    //显示对焦指示器
                    setFocusViewWidthAnimation(event.getX(), event.getY());
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getPointerCount() == 1) {
                    firstTouch = true;
                }
                if (event.getPointerCount() == 2) {
                    //第一个点
                    float point_1_X = event.getX(0);
                    float point_1_Y = event.getY(0);
                    //第二个点
                    float point_2_X = event.getX(1);
                    float point_2_Y = event.getY(1);

                    float result = (float) Math.sqrt(Math.pow(point_1_X - point_2_X, 2) + Math.pow(point_1_Y -
                            point_2_Y, 2));

                    if (firstTouch) {
                        firstTouchLength = result;
                        firstTouch = false;
                    }
                    if ((int) (result - firstTouchLength) / zoomGradient != 0) {
                        firstTouch = true;
                        machine.zoom(result - firstTouchLength, CameraInterface.TYPE_CAPTURE);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                firstTouch = true;
                break;
        }
        return true;
    }

    //对焦框指示器动画
    private void setFocusViewWidthAnimation(float x, float y) {
        machine.foucs(x, y, new CameraInterface.FocusCallback() {
            @Override
            public void focusSuccess() {
                mFoucsView.setVisibility(INVISIBLE);
            }
        });
    }

    /**************************************************
     * 对外提供的API                     *
     **************************************************/

    public void setJCameraLisenter(JCameraListener jCameraLisenter) {
        this.jCameraLisenter = jCameraLisenter;
    }


    private ErrorListener errorLisenter;

    //启动Camera错误回调
    public void setErrorLisenter(ErrorListener errorLisenter) {
        this.errorLisenter = errorLisenter;
        CameraInterface.getInstance().setErrorLinsenter(errorLisenter);
    }


    @Override
    public void resetState(int type) {
        switch (type) {
            case TYPE_PICTURE:
                mPhoto.setVisibility(INVISIBLE);
                break;
            case TYPE_DEFAULT:
                mVideoView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                break;
        }
        mSwitchCamera.setVisibility(GONE);
        mFlashLamp.setVisibility(VISIBLE);
        mCaptureLayout.resetCaptureLayout();
    }

    @Override
    public void confirmState(int type) {
        switch (type) {
            case TYPE_PICTURE:
                mPhoto.setVisibility(INVISIBLE);
                if (jCameraLisenter != null) {
                    jCameraLisenter.captureSuccess(captureBitmap);
                }
                break;
            case TYPE_DEFAULT:
                break;
        }
        mCaptureLayout.resetCaptureLayout();
    }

    @Override
    public void showPicture(Bitmap bitmap, boolean isVertical) {
        if (isVertical) {
            mPhoto.setScaleType(ImageView.ScaleType.FIT_XY);
        } else {
            mPhoto.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
        captureBitmap = bitmap;
        mPhoto.setImageBitmap(bitmap);
        mPhoto.setVisibility(VISIBLE);
    }

    @Override
    public boolean handlerFoucs(float x, float y) {
        if (y > mCaptureLayout.getTop()) {
            return false;
        }
        mFoucsView.setVisibility(VISIBLE);
        if (x < mFoucsView.getWidth() / 2) {
            x = mFoucsView.getWidth() / 2;
        }
        if (x > layout_width - mFoucsView.getWidth() / 2) {
            x = layout_width - mFoucsView.getWidth() / 2;
        }
        if (y < mFoucsView.getWidth() / 2) {
            y = mFoucsView.getWidth() / 2;
        }
        if (y > mCaptureLayout.getTop() - mFoucsView.getWidth() / 2) {
            y = mCaptureLayout.getTop() - mFoucsView.getWidth() / 2;
        }
        mFoucsView.setX(x - mFoucsView.getWidth() / 2);
        mFoucsView.setY(y - mFoucsView.getHeight() / 2);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mFoucsView, "scaleX", 1, 0.6f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mFoucsView, "scaleY", 1, 0.6f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(mFoucsView, "alpha", 1f, 0.4f, 1f, 0.4f, 1f, 0.4f, 1f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(scaleX).with(scaleY).before(alpha);
        animSet.setDuration(400);
        animSet.start();
        return true;
    }

    public void setLeftClickListener(ClickListener clickListener) {
        this.leftClickListener = clickListener;
    }

    public void setRightClickListener(ClickListener clickListener) {
        this.rightClickListener = clickListener;
    }

    private void setFlashRes() {
        switch (type_flash) {
            case TYPE_FLASH_AUTO:
                mFlashLamp.setImageResource(R.drawable.ic_flash_auto);
                machine.flash(Camera.Parameters.FLASH_MODE_AUTO);
                break;
            case TYPE_FLASH_ON:
                mFlashLamp.setImageResource(R.drawable.ic_flash_on);
                machine.flash(Camera.Parameters.FLASH_MODE_ON);
                break;
            case TYPE_FLASH_OFF:
                mFlashLamp.setImageResource(R.drawable.ic_flash_off);
                machine.flash(Camera.Parameters.FLASH_MODE_OFF);
                break;
        }
    }
}
