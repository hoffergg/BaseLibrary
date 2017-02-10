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
    void setToken();

}
