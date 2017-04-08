package com.example.lilactests;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.lilactests.app.LilacTestsApp;

import static com.example.lilactests.utils.PrefUtils.initPrefData;

public class HelloPageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.background_splash);
        //调用Glide来加载图片，避免溢出
        ImageView splashImage = (ImageView) findViewById(R.id.splash_background);
        Glide.with(LilacTestsApp.getContext()).load(R.drawable.background_lilactests).into(splashImage);

        Integer time = 2000;    //设置等待时间，单位为毫秒
        Handler handler = new Handler();
        //当计时结束时，跳转至主界面
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(HelloPageActivity.this, LoginActivity.class);
                initPrefData();
                startActivity(intent);
                //结束当前的 Activity
                HelloPageActivity.this.finish();
            }
        }, time);
    }

}
