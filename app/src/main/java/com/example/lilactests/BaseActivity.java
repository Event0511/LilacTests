package com.example.lilactests;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.lilactests.app.LilacTestsApp;
import com.example.lilactests.utils.ActivityCollector;

/**
 *  Created by Eventory on 2017/2/4 0004.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTheme(LilacTestsApp.getAppTheme());
        Log.d("BaseActivity", getClass().getSimpleName());
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    protected void onTheme() {

    }
}
