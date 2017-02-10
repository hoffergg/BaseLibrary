package com.dailycation.base.view;

import android.view.View;

/**
 * Created by hoffer on 17/1/21.
 */

public interface ILoginView extends IBaseView{
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
}
