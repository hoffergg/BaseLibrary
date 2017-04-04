package com.dailycation.base.http;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hehu on 17-1-6.
 */

@Deprecated
public class Resp {
    @SerializedName("result")
    int statusCode;
    int errorCode;
    String errorDesp;

    public int getStatusCode() {
        return statusCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorDesp() {
        return errorDesp;
    }
}
