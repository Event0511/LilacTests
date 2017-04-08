package com.example.lilactests.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 *  Created by Eventory on 2017/2/4 0004.
 */

public class ActivityCollector {
    private static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static List<Activity> getActivities() {
        return activities;
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    public static void recreateAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.recreate();
            }
        }

    }
}
