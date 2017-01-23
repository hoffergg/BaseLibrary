package com.dailycation.base.callback;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Activity生命周期处理,获取到当前的Activity
 * Created by hehu on 16-12-30.
 */

public class MyActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    MyApplication mMyApplication;
    Activity mCurrentActivity;

    public MyActivityLifecycleCallbacks(MyApplication mMyApplication, Activity mCurrentActivity) {
        this.mMyApplication = mMyApplication;
        this.mCurrentActivity = mCurrentActivity;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        mCurrentActivity = activity;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        if (activity == mCurrentActivity)
            mCurrentActivity = null;
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (activity == mCurrentActivity)
            mCurrentActivity = null;
    }
}
