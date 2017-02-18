package com.dailycation.base;

import android.app.Application;

/**
 * Created by hoffer on 17/1/21.
 */

public class BaseApplication extends Application{
    private static BaseApplication instance;

    public static BaseApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
