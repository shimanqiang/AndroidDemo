package com.shi.weixinhot.application;

import android.app.Application;
import android.content.Context;

import com.shi.weixinhot.tools.LogUtil;

/**
 * Created by Administrator on 2016/12/25.
 */

public class WeiXinHotApplication extends Application {
    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.LEVEL = LogUtil.VERBOSE;
        context = getApplicationContext();

        CrashHandler crashHandler = CrashHandler.getInstance();
        // 注册crashHandler
        crashHandler.init(context);
    }
}
