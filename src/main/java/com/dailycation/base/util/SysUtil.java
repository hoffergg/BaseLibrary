package com.dailycation.base.util;

import android.content.pm.PackageManager;

/**
 * Created by hehu on 18-3-16.
 */

public class SysUtil {
    public static boolean isPackageInstalled(String packagename, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packagename, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
