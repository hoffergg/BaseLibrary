package com.dailycation.base.data.source;

/**
 * Http接口获取结果异常类(JSON解析后)
 * Created by hehu on 16-12-27.
 */

public class HttpResultException extends RuntimeException{
    private int code;
    private String message;
    public final static int RESPONSE_NO_DATA = 101;

    public HttpResultException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
