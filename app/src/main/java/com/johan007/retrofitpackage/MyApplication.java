package com.johan007.retrofitpackage;

import android.app.Application;


/**
 * Created by Johan007 on 2017/6/2.
 */

public class MyApplication extends Application {
    public static MyApplication context;
    @Override
    public void onCreate() {
        super.onCreate();
        if (context == null) {
            context = this;
        }
    }
}
