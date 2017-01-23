package com.dailycation.base.http;

import rx.Observable;
import rx.Subscriber;

/**
 * 请求参数组装遇到对象为null的OnSubscribe
 * Created by hehu on 17-1-5.
 */

public class RequestParamNullOnSubscribe<T> implements Observable.OnSubscribe<Subscriber<T>>{
    public RequestParamNullOnSubscribe() {
    }

    @Override
    public void call(Subscriber<? super Subscriber<T>> subscriber) {
        subscriber.onError(new RequestParamNullException());
    }
}
