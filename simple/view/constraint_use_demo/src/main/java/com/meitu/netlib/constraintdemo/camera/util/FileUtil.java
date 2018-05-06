package com.meitu.netlib.constraintdemo.camera.util;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 */
public class FileUtil {
    public static final String FOLDER = "autoLite";

    public static void initFolder() {
        File imgFile = new File(getCameraImageFolder());
        if (!imgFile.exists() || imgFile.isFile()) {
            imgFile.mkdirs();
        }
    }

    public static String saveBitmap(Bitmap b) {
        String fileName = getCameraImgPath();
        File f = new File(fileName);
        try {
            f.createNewFile();
            FileOutputStream fout = new FileOutputStream(f);
            BufferedOutputStream bos = new BufferedOutputStream(fout);
            b.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            return f.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 检测SD卡是否存在
     */
    public static boolean checkSDcard() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());
    }

    /**
     * 根据时间获取生成的相机照片路径
     *
     * @return
     */
    public static String getCameraImgPath() {
        initFolder();
        return getCameraImageFolder() + "/" + System.currentTimeMillis() + ".jpg";
    }

    public static String getCameraImageFolder() {
        return getAppFoler() + "/" + "cameraImg";
    }

    public static String getAppFoler() {
        return Environment.getExternalStorageDirectory()
                + "/" + FOLDER;
    }

}
