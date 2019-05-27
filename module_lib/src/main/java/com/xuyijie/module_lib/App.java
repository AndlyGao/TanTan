package com.xuyijie.module_lib;

import android.app.Application;
import android.util.DisplayMetrics;

import com.xuyijie.module_lib.config.AppInitialUtil;

import cn.jpush.im.android.api.JMessageClient;

public class App extends Application {
    private static App instance;
    /**
     * 屏幕宽度
     */
    public static int screenWidth;
    /**
     * 屏幕高度
     */
    public static int screenHeight;
    /**
     * 屏幕密度
     */
    public static float screenDensity;
    public static App getInstance() {
        if (instance == null) {
            return new App();
        }
        return instance;
    }
    private void initScreenSize() {
        DisplayMetrics curMetrics = getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = curMetrics.widthPixels;
        screenHeight = curMetrics.heightPixels;
        screenDensity = curMetrics.density;
    }
    private AppInitialUtil appInitialUtil = new AppInitialUtil();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        initScreenSize();
        appInitialUtil.initJPush()
                .initArouter()
                .initBmob()
                .initJPushMessage()
                .initToast();
    }

}
