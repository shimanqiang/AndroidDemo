package com.smq.demo;

import android.app.Application;
import android.content.Context;

/**
 * Created by shimanqiang on 16/12/22.
 */

public class AssortedApplication extends Application {
    private static Context context;
    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
