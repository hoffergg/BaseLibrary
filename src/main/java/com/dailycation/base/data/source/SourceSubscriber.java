package com.dailycation.base.data.source;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.dailycation.base.BaseApplication;
import com.dailycation.base.helper.LogHelper;
import com.dailycation.base.http.RequestParamNullException;
import com.dailycation.base.util.ToastUtil;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * 自定义资源获取Subscriber
 * Created by hehu on 16-12-27.
 */

public abstract class SourceSubscriber<T> extends Subscriber<T> {
    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        try {
            SourceException ex = null;
            if (e instanceof RequestParamNullException) {
                //请求参数组装时遇到null错误
                ex = new SourceException(e, SourceException.REQUEST_PARAM_NULL_ERROR, "Request Make Params Encounter Null Exception");
            }  else if (e instanceof HttpException) {
                //HTTP错误
                HttpException httpException = (HttpException) e;
                ex = new SourceException(e, httpException.code(), httpException.message());
                if (httpException.code() == SourceException.UNAUTHORIZED) {
                    BaseApplication.getInstance().onUserUnauthorized();
                }
            } else if (e instanceof HttpResultException) {
                //服务器返回的错误
                HttpResultException resultException = (HttpResultException) e;
                ex = new SourceException(resultException, resultException.getCode(), resultException.getMessage());
            } else if (e instanceof JsonParseException
                    || e instanceof JSONException
                    || e instanceof ParseException) {
                //解析错误
                ex = new SourceException(e, SourceException.PARSE_ERROR, "Parse Error");
            } else if (e instanceof IOException) {
                if (!isNetworkOn())
                    //没有网络
                    onNoNet();
                else
                    //没有任何返回数据，无法解析域名等情况
                    ex = new SourceException(e, SourceException.NETWORK_IO_ERROR, "Connect Server Error");
            } else {
                //未知错误
                ex = new SourceException(e, SourceException.UNKNOWN_ERROR, "Unknown Error");
            }
            if (ex != null) {
                LogHelper.e(LogHelper.TAG_SOURCE, ex.toString());
                Toast.makeText(BaseApplication.getInstance(), ex.toString(), Toast.LENGTH_SHORT).show();
                onError(ex);
            }
        } catch (Exception e2){
            e2.printStackTrace();
        }
    }

    /**
     * 检查网络是否开启
     * @return
     */
    public boolean isNetworkOn(){
        ConnectivityManager cm = (ConnectivityManager) BaseApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnectedOrConnecting());
    }

    protected abstract void onError(SourceException e);

    /**
     * 没有网络
     */
    protected abstract void onNoNet();

//    protected boolean isRespNull(BaseResp resp){
//        return resp.getData().isJsonNull();
//    }

}
