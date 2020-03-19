package com.example.lilactests.app;

import android.app.Application;
import android.content.Context;

import com.example.lilactests.R;
import com.example.lilactests.utils.PrefUtils;

import org.litepal.LitePalApplication;

/**
 *  Created by Eventory on 2017/2/10 0010.
 */

public class LilacTestsApp extends Application {
    private static Context context;
    private static int appTheme;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LitePalApplication.initialize(context);
    }

    public static Context getContext() {
        return context;
    }

    public static int getAppTheme() {
        if (PrefUtils.isNightMode()) {
            appTheme = R.style.NightModeAppTheme;
        } else {
            appTheme = R.style.DefaultAppTheme;
        }
        return appTheme;
    }

    public static void setAppTheme(int appTheme) {
        LilacTestsApp.appTheme = appTheme;
    }
}
