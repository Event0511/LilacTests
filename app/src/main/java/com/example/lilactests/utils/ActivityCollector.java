package com.example.lilactests.utils;

import android.app.Activity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    /**
     * 调用所有活动的同名方法
     * @param methodName 方法名
     * @param args 方法参数
     * @return 如果成功执行，返回1，如果发生异常根据情况分为：-1：无对应方法； -2：非法访问； -3：错误调用目标
     */
    public static int callAllMethod(String methodName, Object... args) {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                try {
                    if (args.length != 0) {
                        Method method = activity.getClass().getDeclaredMethod(methodName, args.getClass());
                        method.invoke(activity, args);
                    } else {
                        Method method = activity.getClass().getDeclaredMethod(methodName);
                        method.invoke(activity);
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                    return -1;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return -2;
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                    return -3;
                }
            }
        }
        return 1;
    }
}
