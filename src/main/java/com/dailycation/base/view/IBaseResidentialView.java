package com.dailycation.base.view;

import android.view.View;

import com.dailycation.base.model.IUser;

/**
 * Created by hehu on 17-6-7.
 */

public interface IBaseResidentialView extends IBaseView{

    /**
     * 登录成功
     */
    void onLoginSuccess();

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

    /**
     * 登录错误提示，包括格式错误和服务器返回错误等。
     * @param code 错误代码
     * @param desc 错误描述
     */
    public void showLoginError(int code,String desc);

    /**
     * 显示本地记录数据
     * @param user
     */
    public void showLocalData(IUser user);

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

}
