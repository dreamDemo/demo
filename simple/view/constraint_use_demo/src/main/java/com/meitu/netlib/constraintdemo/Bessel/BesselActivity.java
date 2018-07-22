package com.meitu.netlib.constraintdemo.Bessel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.meitu.netlib.constraintdemo.R;

public class BesselActivity extends Activity{

    public static void launch(Activity activity) {
        activity.startActivity(new Intent(activity, BesselActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bessel);
        findViewById(R.id.tv_bessel1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bessel1Activity.launch((Activity) v.getContext());
            }
        });
        findViewById(R.id.tv_bessel2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bessel2Activity.launch((Activity) v.getContext());
            }
        });
    }
}
