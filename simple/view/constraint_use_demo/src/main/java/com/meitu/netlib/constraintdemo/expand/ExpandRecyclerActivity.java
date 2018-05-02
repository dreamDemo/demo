package com.meitu.netlib.constraintdemo.expand;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.meitu.netlib.constraintdemo.R;
import com.meitu.netlib.constraintdemo.expand.adapter.SimpleAdapter;
import com.meitu.netlib.constraintdemo.expand.model.ExpandTipModel;
import com.meitu.netlib.constraintdemo.expand.model.PicModelList;
import com.meitu.netlib.constraintdemo.expand.model.SimpleModel;

import java.util.ArrayList;
import java.util.List;

public class ExpandRecyclerActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    public List<SimpleModel> mList = new ArrayList<>();

    public static void launchActivity(Context context) {
        context.startActivity(new Intent(context, ExpandRecyclerActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        recyclerView = (RecyclerView) findViewById(R.id.rv_main_test);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(new SimpleAdapter(this, mList));
    }

    private void initData() {
        PicModelList picModelList = new PicModelList();
        for (int i = 0; i < 5; i++) {
            picModelList.picModelList.add(new PicModelList.PicModel(R.mipmap.incent_sign_in_dialog_7_bg));
        }
        mList.add(picModelList);
        ExpandTipModel expandTipModel = new ExpandTipModel();
        expandTipModel.show = "展示";
        mList.add(expandTipModel);
    }

}
