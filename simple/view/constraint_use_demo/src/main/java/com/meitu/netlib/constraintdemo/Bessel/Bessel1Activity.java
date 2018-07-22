package com.meitu.netlib.constraintdemo.Bessel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.meitu.netlib.constraintdemo.R;

public class Bessel1Activity extends Activity {

    public static void launch(Activity activity) {
        activity.startActivity(new Intent(activity, Bessel1Activity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bessel1);
    }
}
