package com.dailycation.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.pm.PackageManager;

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

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initDebugTools();
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
