package com.meitu.netlib.constraintdemo.expand.adapter.holder;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.meitu.netlib.constraintdemo.R;
import com.meitu.netlib.constraintdemo.expand.adapter.SimpleAdapter;
import com.meitu.netlib.constraintdemo.expand.model.PicModelList;
import com.meitu.netlib.constraintdemo.expand.model.SimpleModel;

public class ListHolder extends SimpleHolder {
    public RecyclerView mRlExpand;

    public ListHolder(View view) {
        super(view);
        mRlExpand = (RecyclerView) view.findViewById(R.id.rl_list);
        mRlExpand.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
    }

    @Override
    public void onBindeHolder(SimpleModel simpleModel) {
        mRlExpand.setLayoutManager(new GridLayoutManager(mRlExpand.getContext(), 2));
        mRlExpand.setAdapter(new SimpleAdapter(mRlExpand.getContext(), ((PicModelList) simpleModel).picModelList));
    }

}