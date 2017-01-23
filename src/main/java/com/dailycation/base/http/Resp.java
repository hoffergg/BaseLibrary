package com.dailycation.base.http;

/**
 * Created by hehu on 17-1-6.
 */

public class Resp {
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

    @Override
    public String toString() {
        return "Resp{" +
                "statusCode=" + statusCode +
                ", errorCode=" + errorCode +
                ", errorDesp='" + errorDesp + '\'' +
                '}';
    }
}
