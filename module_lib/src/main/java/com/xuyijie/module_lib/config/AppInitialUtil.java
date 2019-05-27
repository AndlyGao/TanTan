package com.xuyijie.module_lib.config;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xuyijie.module_lib.App;
import com.xuyijie.module_lib.view.toast.ToastUtils;

import cn.bmob.v3.Bmob;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;

public class AppInitialUtil {
    public AppInitialUtil initJPush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(App.getInstance());
        return this;
    }

    public AppInitialUtil initJPushMessage() {
        JMessageClient.setDebugMode(true);
        JMessageClient.init(App.getInstance());
        return this;
    }

    public AppInitialUtil initArouter() {
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();
        ARouter.init(App.getInstance());

        return this;
    }


    public AppInitialUtil initToast() {
        ToastUtils.init(App.getInstance());
        return this;
    }
    public AppInitialUtil initBmob() {
        Bmob.initialize(App.getInstance(),"d4f3b22f28d96942bc25a2c1e1eb16ca");
        return this;
    }

}
