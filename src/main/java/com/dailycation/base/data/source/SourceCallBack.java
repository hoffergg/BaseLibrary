package com.dailycation.base.data.source;

/**
 * 资源基础回调接口
 *
 * Created by hehu on 16-12-23.
 */

public interface SourceCallBack {
    void onLoadSuccess(Object o);
    void onLoadError(SourceException error);
}
