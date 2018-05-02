package com.meitu.netlib.constraintdemo.expand.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.meitu.netlib.constraintdemo.expand.model.SimpleModel;

/**
 * Created by sunyuxin on 2018/4/15.
 */

public abstract class SimpleHolder extends RecyclerView.ViewHolder{

    public SimpleHolder(View itemView) {
        super(itemView);
    }

    public abstract void onBindeHolder(SimpleModel simpleModel);

    public ItemClickListener listener;

    public interface ItemClickListener {
        void onItemClick(int position);
    }
}
