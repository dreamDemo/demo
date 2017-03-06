package com.example.imageloader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initImageLoader();
    }

    private void initImageLoader() {
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this.getApplication()));
    }
}
