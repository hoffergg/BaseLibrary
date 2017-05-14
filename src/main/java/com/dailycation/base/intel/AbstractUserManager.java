package com.dailycation.base.intel;

import com.dailycation.base.model.IUser;

/**
 * Created by hoffer on 17/5/13.
 */

public abstract class AbstractUserManager {

    protected IUser mCurrentUser;

    /**
     * get current user
     * @return
     */
    public IUser getCurrentUser(){
        return mCurrentUser;
    }

    protected abstract void login(String userName, String pass);

    protected abstract void logout();

    public boolean isLogin(){
        return mCurrentUser!=null && mCurrentUser.isLogin();
    }

    /**
     * init user
     * @param user
     */
    protected abstract void initUser(IUser user);
}
