package com.dailycation.base.view;

import android.view.View;

/**
 * Created by hoffer on 17/1/21.
 */

public interface IRegisterView extends IBaseView{
    /**
     * 注册成功
     */
    void onRegisterSuccess(String userName);

    /**
     * 输入格式错误
     * @param error 错误提示
     * @param code 错误代码
     */
    void showInputError(String error,int code);

    /**
     * 点击登录
     * @param view
     */
    public void onLogin(View view);

    /**
     * 点击注册
     * @param view
     */
    public void onRegister(View view);
}
