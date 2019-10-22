package com.dailycation.base.util;

import android.widget.Toast;

import com.dailycation.base.BaseApplication;

/**
 * Created by hoffer on 17/2/10.
 */

public class ToastUtil {
    public static void showToast(int message){
        Toast.makeText(BaseApplication.getInstance(),message,Toast.LENGTH_SHORT).show();
    }

    public static void showToast(String message){
        Toast.makeText(BaseApplication.getInstance(),message,Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(String message){
        Toast.makeText(BaseApplication.getInstance(),message,Toast.LENGTH_LONG).show();
    }

    public static void showToast(String message,int length){
        Toast.makeText(BaseApplication.getInstance(),message,length).show();
    }
}
