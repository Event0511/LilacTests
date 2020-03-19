package com.example.lilactests;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.PersistableBundle;
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
        Log.d("BaseActivity", "------------" + getClass().getSimpleName() + " onCreate-------");
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        Log.d("BaseActivity", "------------" + getClass().getSimpleName() + " onDestroy-------");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("BaseActivity", "------------" + getClass().getSimpleName() + " onRestart-------");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("BaseActivity", "------------" + getClass().getSimpleName() + " onStart-------");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("BaseActivity", "------------" + getClass().getSimpleName() + " onStop-------");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("BaseActivity", "------------" + getClass().getSimpleName() + " onPause-------");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("BaseActivity", "------------" + getClass().getSimpleName() + " onResume-------");
    }

    protected void onTheme() {

    }
}
