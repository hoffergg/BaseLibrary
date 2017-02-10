package com.dailycation.base.presenter;

import com.dailycation.base.model.IUser;

/**
 * Created by hoffer on 17/2/9.
 */

public interface ILoginPresenter extends IBasePresenter{
    /**
     * 登录
     * @param userName 用户名
     * @param password 密码
     * @param verifyCode 验证码
     */
    void login(String userName,String password,String verifyCode);

    /**
     * 登录成功
     * @param user 登录成功返回的用户
     */
    void onLoginSuccess(IUser user);

    /**
     * 检查输入
     * @param userName
     * @param password
     * @param verifyCode
     * @return
     */
    boolean checkInput(String userName,String password,String verifyCode);
}
