package com.meitu.netlib.volleydemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    private RequestQueue mRequestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((WebView)findViewById(R.id.web_view)).loadUrl("http://e.cn.miaozhen.com/r/k=2031509&p=736VO&dx=__IPDX__&rt=2&ns=__IP__&ni=__IESID__&v=__LOC__&xa=__ADPLATFORM__&ro=sm&vo=3a1174e89&vr=2&o=http%3A%2F%2Fwww.kotex-km.com%2Fact%2Fhello_kitty%2Findex.html%3Futm_source%3Dmeitu2");
        initVolley();
        addRequest();
    }

    private void addRequest() {
    }

    public void initVolley() {
        mRequestQueue = Volley.newRequestQueue(this);
    }

}
