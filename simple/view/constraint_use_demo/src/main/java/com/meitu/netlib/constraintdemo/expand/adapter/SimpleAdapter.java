package com.meitu.netlib.constraintdemo.expand.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.meitu.netlib.constraintdemo.R;
import com.meitu.netlib.constraintdemo.expand.adapter.holder.ExpandTipShowHolder;
import com.meitu.netlib.constraintdemo.expand.adapter.holder.ListHolder;
import com.meitu.netlib.constraintdemo.expand.adapter.holder.PicHolder;
import com.meitu.netlib.constraintdemo.expand.adapter.holder.SimpleHolder;
import com.meitu.netlib.constraintdemo.expand.model.ExpandTipModel;
import com.meitu.netlib.constraintdemo.expand.model.PicModelList;
import com.meitu.netlib.constraintdemo.expand.model.SimpleModel;

import java.util.List;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleHolder> {

    LayoutInflater mLayoutInflater;
    List<SimpleModel> mList;

    public SimpleAdapter(Context context, List<SimpleModel> list) {
        this.mList = list;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public SimpleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == SimpleModel.TYPE_LIST) {
            return new ListHolder(mLayoutInflater.inflate(R.layout.expend_list, null));
        } else if (viewType == SimpleModel.TYPE_PIC) {
            return new PicHolder(mLayoutInflater.inflate(R.layout.expend_pic_item, null));
        } else if (viewType == SimpleModel.TYPE_EXPAND_TIP) {
            ExpandTipShowHolder expandTipShowHolder = new ExpandTipShowHolder(mLayoutInflater.inflate(R.layout.expend_text_tip, null));
            expandTipShowHolder.setItemClickListener(new SimpleHolder.ItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    int index = mList.size() - 1;
                    for (int i = 0; i < 5; i++) {
                        ExpandTipModel expandTipModel = new ExpandTipModel();
                        expandTipModel.show = "ceshi " + i;
                        mList.add(expandTipModel);
                    }

                    notifyItemRangeChanged(index, 5);
                }
            });
            return expandTipShowHolder;
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(SimpleHolder holder, int position) {
        holder.onBindeHolder(mList.get(position));
    }


    @Override
    public int getItemViewType(int position) {
        return mList.get(position).type;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}