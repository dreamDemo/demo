package com.meitu.netlib.constraintdemo.expand.adapter.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meitu.netlib.constraintdemo.R;
import com.meitu.netlib.constraintdemo.expand.model.ExpandTipModel;
import com.meitu.netlib.constraintdemo.expand.model.PicModelList;
import com.meitu.netlib.constraintdemo.expand.model.SimpleModel;

public class ExpandTipShowHolder extends SimpleHolder {
    public TextView textView;

    public ExpandTipShowHolder(View view) {
        super(view);
        textView = (TextView) view.findViewById(R.id.tv_show);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(getLayoutPosition());
                }
            }
        });
    }

    @Override
    public void onBindeHolder(SimpleModel simpleModel) {
        ExpandTipModel expandModel = (ExpandTipModel) simpleModel;
        textView.setText(expandModel.show);
    }

    public void setItemClickListener (SimpleHolder.ItemClickListener listener) {
        this.listener = listener;
    }

}