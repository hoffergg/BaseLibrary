package com.dailycation.base;

/**
 * Created by hehu on 17-3-4.
 */

public interface BaseCallBack {

    /**
     * user has no auth,token may not valid
     */
    void onUserUnauthorized();
}
