package com.meitu.netlib.constraintdemo.expand.adapter.holder;

import android.view.View;
import android.widget.ImageView;

import com.meitu.netlib.constraintdemo.R;
import com.meitu.netlib.constraintdemo.expand.model.PicModelList;
import com.meitu.netlib.constraintdemo.expand.model.SimpleModel;

public class PicHolder extends SimpleHolder {
    public ImageView imageView;

    public PicHolder(View view) {
        super(view);
        imageView = (ImageView) view.findViewById(R.id.iv_test);
    }

    @Override
    public void onBindeHolder(SimpleModel simpleModel) {
        PicModelList.PicModel picModel = (PicModelList.PicModel) simpleModel;
        imageView.setImageResource(picModel.resId);
    }

}