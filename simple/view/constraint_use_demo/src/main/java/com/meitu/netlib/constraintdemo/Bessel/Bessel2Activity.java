package com.meitu.netlib.constraintdemo.Bessel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.meitu.netlib.constraintdemo.R;

public class Bessel2Activity extends Activity {

    public static void launch(Activity activity) {
        activity.startActivity(new Intent(activity, Bessel2Activity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bessel2);
        final Bessel2 bessel2 = (Bessel2) findViewById(R.id.bessel2);
        findViewById(R.id.control1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bessel2.setMode(true);
            }
        });
        findViewById(R.id.control2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bessel2.setMode(false);
            }
        });
    }
}
