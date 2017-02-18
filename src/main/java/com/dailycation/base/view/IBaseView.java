package com.dailycation.base.view;

/**
 * Created by hoffer on 17/1/21.
 *
 */

public interface IBaseView {

    /**
     * update loading UI
     * @param show
     */
    void showLoading(boolean show);

    /**
     * update UI when there is no network
     */
    void showNoNet();

    /**
     * update UI when there is no data
     */
    void showNoData();

    /**
     * show some text message
     * @param msg
     */
    void showMessage(String msg);
}
