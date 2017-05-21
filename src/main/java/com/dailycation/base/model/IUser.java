package com.dailycation.base.model;

/**
 * User model
 * Created by hoffer on 17/2/9.
 */

public interface IUser {
    /**
     * get user login token
     * @return
     */
    String getToken();

    /**
     * set user token
     */
    void setToken(String token);

    /**
     * is logged in
     * @return
     */
    boolean isLogin();

    /**
     * set login status
     * @param login
     */
    void setLoginStatus(boolean login);

    /**
     * get user id
     */
    long getUserId();

    /**
     * get user account
     */
    String getUserAccount();

    /**
     * set user account
     */
    void setUserAccount(String userAccount);

    void logout();

    String getPassword();

    void setPassword(String pass);
}
