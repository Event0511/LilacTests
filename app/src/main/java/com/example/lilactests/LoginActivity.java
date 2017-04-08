package com.example.lilactests;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.lilactests.app.LilacTestsApp;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        //调用Glide来加载图片，避免溢出
        ImageView splashImage = (ImageView) findViewById(R.id.splash_background);
        Glide.with(LilacTestsApp.getContext()).load(R.drawable.background_lilac).into(splashImage);

        Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainLayoutActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });
    }

}
