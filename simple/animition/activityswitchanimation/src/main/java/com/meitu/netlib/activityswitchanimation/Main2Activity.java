package com.meitu.netlib.activityswitchanimation;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

public class Main2Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        enterAnimation();
    }

    private void enterAnimation() {
//        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(findViewById(R.id.rl_background), "alpha", 255, 0);
//        objectAnimator.setInterpolator(new DecelerateInterpolator());
//        objectAnimator.setDuration(1000);
//        objectAnimator.start();
    }

}
