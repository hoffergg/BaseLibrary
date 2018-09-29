package com.dailycation.base.view;

import android.content.Context;

public interface IBaseNetView {
    void showLoading(boolean show);
    void initView();
    void showNoNet();
    Context getContext();
}
