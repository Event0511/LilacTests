package com.example.lilactests.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.lilactests.app.LilacTestsApp;

/**
 * Created by Administrator on 2017/2/8 0008.
 */

public class PrefUtils {

    //日间或者夜间模式
    private static final String PRE_THEME_MODE = "night_mode";

    //省流量模式 这儿和R.string.save_net_mode相同
    private static final String PRE_SAVE_NET_MODE = "save_net_mode";

    //SharedPreference库名字
    private static final String PRE_NAME = "com.example.lilactests";


    /**
     * 初始化SharedPreference库各项为默认状态
     */
    public static void initPrefData() {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        int launchCount = getSharedPreferences().getInt("launchCount", 0);
        if (launchCount != 0) {
            editor.putInt("launchCount", launchCount+1);
        } else { // 第一次启动应用初始化SharedPreferenc库的默认值
            editor.putInt("launchCount", 1);
            editor.putString("themeColor", "default");
            editor.putInt("fontSize", 0);
            editor.putBoolean(PRE_SAVE_NET_MODE, true);
            editor.putBoolean(PRE_THEME_MODE, false);
            editor.apply();
        }
    }

    private static SharedPreferences getSharedPreferences() {
        return LilacTestsApp.getContext()
                .getSharedPreferences(PRE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 设置省流量模式
     *
     * @param isSavingNetMode true为省流量模式，不加载图片
     */
    public static void setSaveNetMode(boolean isSavingNetMode) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(PRE_SAVE_NET_MODE, isSavingNetMode);
        editor.apply();
    }

    /**
     * 设置夜间模式
     *
     * @param isNightMode true为夜间模式
     */
    public static void setNightMode(boolean isNightMode) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(PRE_THEME_MODE, isNightMode);
        editor.apply();
    }

    /**
     * 判断省流量模式
     *
     * @return
     */
    public static boolean isSavingNetMode() {
        return getSharedPreferences().getBoolean(PRE_SAVE_NET_MODE, false);
    }

    /**
     * 判断夜间模式
     *
     * @return
     */
    public static boolean isNightMode() {
        return getSharedPreferences().getBoolean(PRE_THEME_MODE, false);
    }

    /**
     * 删除 SharedPreferences 的某个 key
     *
     * @param key
     */
    public static void removeFromPrefs(String key) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.remove(key);
        editor.apply();
    }
}
