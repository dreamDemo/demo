package com.meitu.netlib.activityswitchanimation;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    CoordinatorLayout coordinatorLayout;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
//                coordinatorLayout.setPivotX(0);
//                coordinatorLayout.setPivotY(0);
//                coordinatorLayout.animate().scaleX(0).scaleY(0).translationX(300).translationY(1500).withEndAction(new Runnable() {
//                    @Override
//                    public void run() {
//                    }
//                }).setDuration(1000).setInterpolator(new DecelerateInterpolator()).start();

                startActivity(new Intent(MainActivity.this, Main2Activity.class));
                overridePendingTransition(R.anim.enteralpha, R.anim.exitalpha);
            }
        });
    }

}
