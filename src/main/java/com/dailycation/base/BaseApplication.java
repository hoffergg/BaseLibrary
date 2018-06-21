package com.dailycation.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.dailycation.base.model.IUser;

import java.util.Iterator;
import java.util.List;

/**
 * Created by hoffer on 17/1/21.
 */

public class BaseApplication extends Application implements IApplication {
    private static BaseApplication instance;

    public static BaseApplication getInstance(){
        return instance;
    }
    protected Activity mCurrentActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initDebugTools();
        registerActivityLifeCallBack();
    }

    public Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    public void registerActivityLifeCallBack(){
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                mCurrentActivity = activity;
            }

            @Override
            public void onActivityResumed(Activity activity) {
                mCurrentActivity = activity;
            }

            @Override
            public void onActivityPaused(Activity activity) {
                if(activity == mCurrentActivity){
                    mCurrentActivity = null;
                }
            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                if(activity == mCurrentActivity){
                    mCurrentActivity = null;
                }
            }
        });
    }

    /**
     * user has no auth,token may not valid
     */
    @Override
    public void onUserUnauthorized() {

    }

    /**
     * init debug tools
     */
    @Override
    public void initDebugTools() {
    }

    @Override
    public IUser getCurrentUser() {
        return null;
    }

    public boolean isMainProcess(){
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        if (processAppName == null ||!processAppName.equalsIgnoreCase(instance.getPackageName())) {
            return false;
        }
        return true;
    }


    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }
}
