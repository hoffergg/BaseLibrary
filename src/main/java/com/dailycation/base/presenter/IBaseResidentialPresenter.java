package com.dailycation.base.presenter;

import com.dailycation.base.model.IUser;

/**
 * Created by hehu on 17-6-7.
 */

public interface IBaseResidentialPresenter extends IBasePresenter2{

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
    boolean checkLoginInput(String userName,String password,String verifyCode);

    /**
     * Go register
     * @param userName
     * @param password
     * @param rePassword
     * @param verifyCode
     */
    void register(String userName,String password,String rePassword,String verifyCode);

    void register(String userName,String password,String rePassword,String verifyCode,String name,int sex);


    /**
     * check all the input
     * @param userName
     * @param password
     * @param rePassword
     * @param verifyCode
     * @return
     */
    boolean checkRegisterInput(String userName,String password,String rePassword,String verifyCode);

    /**
     * request verify code for a phone
     * @param phone
     */
    void requestVerifyCode(String phone);
}
