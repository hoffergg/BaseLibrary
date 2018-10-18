package com.dailycation.base.helper;

import android.widget.Toast;

import com.dailycation.base.BaseApplication;
import com.dailycation.base.R;


/**
 * 显示各类消息，Toast消息，Dialog消息等
 * Created by hehu on 17-1-9.
 */

public class MessageHelper {

    public static BaseApplication getApplicationContext(){
        return BaseApplication.getInstance();
    }



    public static void showMessage(int resId){
        Toast.makeText(getApplicationContext(),getApplicationContext().getString(resId),Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示无网络文字提示
     */
    public static void showNoNet(){
        Toast.makeText(getApplicationContext(),getApplicationContext().getString(R.string.no_net_connected),Toast.LENGTH_SHORT).show();
    }
}
