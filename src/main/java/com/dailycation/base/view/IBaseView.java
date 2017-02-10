package com.dailycation.base.view;

/**
 * Created by hoffer on 17/1/21.
 *
 */

public interface IBaseView {

    /**
     * 是否显示进度条
     * @param show
     */
    void showLoading(boolean show);

    /**
     * 无网络提示
     */
    void showNoNet();

    /**
     *无数据提示
     */
    void showNoData();
}
