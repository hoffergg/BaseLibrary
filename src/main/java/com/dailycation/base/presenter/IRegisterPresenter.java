package com.dailycation.base.presenter;

/**
 * Register Presenter
 * Created by hoffer on 17/2/9.
 */

public interface IRegisterPresenter extends IBasePresenter2{
    /**
     * Go register
     * @param userName
     * @param password
     * @param rePassword
     * @param verifyCode
     */
    void register(String userName,String password,String rePassword,String verifyCode);

    /**
     * check all the input
     * @param userName
     * @param password
     * @param rePassword
     * @param verifyCode
     * @return
     */
    boolean checkInput(String userName,String password,String rePassword,String verifyCode);

    /**
     * request verify code for a phone
     * @param phone
     */
    void requestVerifyCode(String phone);

}
