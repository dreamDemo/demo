package com.meitu.netlib.constraintdemo.camera;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.meitu.netlib.constraintdemo.R;
import com.meitu.netlib.constraintdemo.camera.listener.ClickListener;
import com.meitu.netlib.constraintdemo.camera.listener.JCameraListener;
import com.meitu.netlib.constraintdemo.camera.util.FileUtil;
import com.meitu.netlib.constraintdemo.camera.view.CameraView;

/**
 * Created by sunyuxin on 2018/5/2.
 */

public class CameraActivity extends Activity implements ClickListener, JCameraListener {
    private final static int GET_PERMISSION_REQUEST = 100; //权限申请自定义码
    private final static int GALLERY_REQUEST_CODE = 100; //权限申请自定义码
    private CameraView mCameraView;
    private boolean granted = false;

    public static void launchForResult(Activity context, int requestCode) {
        Intent intent = new Intent(context, CameraActivity.class);
        context.startActivityForResult(intent , requestCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_activity);
        mCameraView = (CameraView) findViewById(R.id.jcameraview);
        initAction();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (granted) {
            mCameraView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCameraView.onPause();
    }

    private void initAction() {
        mCameraView.setJCameraLisenter(this);
        mCameraView.setLeftClickListener(this);
        getPermissions();
    }

    private void choosePhoto() {
        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/jpeg");
        startActivityForResult(intentToPickPic, GALLERY_REQUEST_CODE);
    }

    /**
     * 获取权限
     */
    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //具有权限
                granted = true;
            } else {
                //不具有获取权限，需要进行权限申请
                ActivityCompat.requestPermissions(CameraActivity.this, new String[]{
                        Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, GET_PERMISSION_REQUEST);
                granted = false;
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //全屏显示
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == GET_PERMISSION_REQUEST) {
            int size = 0;
            if (grantResults.length >= 1) {
                //相机权限
                int cameraPermissionResult = grantResults[0];
                boolean cameraPermissionGranted = cameraPermissionResult == PackageManager.PERMISSION_GRANTED;
                if (!cameraPermissionGranted) {
                    size++;
                }
                //sd 卡权限
                int sdPermissionResult = grantResults[1];
                boolean sdPermissionGranted = sdPermissionResult == PackageManager.PERMISSION_GRANTED;
                if (!sdPermissionGranted) {
                    size++;
                }
                if (size == 0) {
                    granted = true;
                    mCameraView.grantedPermisssion();
                } else {
                    Toast.makeText(this, "请到设置-权限管理中开启", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == CameraActivity.RESULT_OK) {
            if (requestCode == GALLERY_REQUEST_CODE) {
                Uri imageUri = data.getData();

                if (imageUri != null) {
                    finishOk(imageUri.getPath());
                }

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick() {
        choosePhoto();
    }

    @Override
    public void captureSuccess(Bitmap bitmap) {
        finishOk(FileUtil.saveBitmap(bitmap));
    }

    private void finishOk(String imagePath) {

        try {
            Intent intent = getIntent();
            intent.putExtra("img_url", imagePath);
            setResult(RESULT_OK, intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
