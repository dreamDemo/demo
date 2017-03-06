package com.meitu.netlib.constraintdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String data = "<div class=\"vH0\"><h1 class=\"uV0 qH0\" id=\"h1Logo\">   <a href=\"javascript:;\" class=\"tt0\"><img src=\"http://mail.qiye.163.com/qiyeimage/logo/meitu_com/1461740234413.png\" alt=\"\" id=\"imgLogo\">   </a></h1><ul class=\"vz0\" role=\"navigation\"></ul></div>";
        WebView webView = (WebView) findViewById(R.id.web_main);
        webView.loadDataWithBaseURL("", data, "text/html", "utf-8", null);

    }
}
