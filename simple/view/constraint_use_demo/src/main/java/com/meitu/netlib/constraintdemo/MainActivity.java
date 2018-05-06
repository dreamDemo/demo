package com.meitu.netlib.constraintdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meitu.netlib.constraintdemo.camera.CameraActivity;
import com.meitu.netlib.constraintdemo.expand.ExpandRecyclerActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    public List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        recyclerView = (RecyclerView) findViewById(R.id.rv_main_test);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(new MainAdapter(this, mList));
    }

    private void initData() {
        mList.add("点击展开RecyclerView");
        mList.add("照相机");
    }

    public class MainAdapter extends RecyclerView.Adapter<MainAdapter.TextHolder> {

        LayoutInflater mLayoutInflater;
        List<String> mList;

        public MainAdapter(Context context, List<String> list) {
            this.mList = list;
            mLayoutInflater = LayoutInflater.from(context);
        }


        @Override
        public TextHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new TextHolder(mLayoutInflater.inflate(R.layout.item_test, null));
        }

        @Override
        public void onBindViewHolder(TextHolder holder, int position) {
            holder.textView.setText(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        class TextHolder extends RecyclerView.ViewHolder {
            public TextView textView;

            public TextHolder(View view) {
                super(view);
                textView = (TextView) view.findViewById(R.id.tv_test);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (getLayoutPosition()) {
                            case 0 :
                                ExpandRecyclerActivity.launchActivity(v.getContext());
                                break;
                            case 1 :
                                CameraActivity.launchForResult((Activity) v.getContext(), 101);
                                break;
                        }
                    }
                });
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 101) {
                Log.d("hahaha", data.getStringExtra("img_url"));
            }
        }
    }
}
