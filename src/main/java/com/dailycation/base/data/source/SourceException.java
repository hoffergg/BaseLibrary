package com.dailycation.base.data.source;

/**
 * 资源获取异常类
 * Created by hehu on 16-12-23.
 */

public class SourceException extends Exception{
    /**
     * http exception code
     */
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int REQUEST_TIMEOUT = 408;
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int BAD_GATEWAY = 502;
    public static final int SERVICE_UNAVAILABLE = 503;
    public static final int GATEWAY_TIMEOUT = 504;

    /**
     * other code
     */
    public final static int NETWORK_NOT_ON = 600;
    public final static int PARSE_ERROR = 601;
    public final static int UNKNOWN_ERROR = 602;
    public final static int NETWORK_IO_ERROR = 603;
    public final static int REQUEST_ERROR = 604;
    public final static int REQUEST_PARAM_NULL_ERROR = 605;
    public static final int SAVE_LOCAL_EMPTY = 606;


    private int code;
    private String message;

    public SourceException(String detailMessage, int code, String message) {
        super(detailMessage);
        this.code = code;
        this.message = message;
    }

    public SourceException(Throwable throwable, int code, String message) {
        super(throwable);
        this.code = code;
        this.message = message;
    }

    public SourceException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public SourceException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "SourceException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
