package com.dailycation.base.view;

/**
 * Created by hoffer on 17/1/21.
 */

public interface IRegisterView {
    void showRegisterSuccess();

    /**
     * 输入格式错误
     * @param error 错误提示
     * @param code 错误代码
     */
    void showInputError(String error,int code);
}
