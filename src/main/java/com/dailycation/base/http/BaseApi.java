package com.dailycation.base.http;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * 基础调用入口
 * Created by hehu on 16-12-22.
 */

public abstract class BaseApi {
    private static final String API_VERSION = "3.0";
    private static final String API_SERVER = BuildConfig.HTTP_HOST;
    private static Retrofit.Builder mRetrofitBuilder = new Retrofit.Builder()
            .baseUrl(API_SERVER)
            .addConverterFactory(MyGsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

    /**
     * 创建Retrofit接口Service
     * @param serviceClass
     * @param <S>
     * @return
     */
    public static <S> S createService(Class<S> serviceClass) {
        final MyApplication application = MyApplication.getMyApplication();
        final Userable user = application.getCurrentUser();

        //Http头部截断器
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String token = null,userId = null;
                String deviceId = SystemUtil.getDeviceId(application);
                String time = String.valueOf(System.currentTimeMillis());
                String sign = null;
                if(user!=null) {
                    token = user.getToken();
                    userId = String.valueOf(user.getUserId());
                    sign = createSign(userId,time,token);
                }
                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("Content-Type", "application/json")
                        .addHeader("appVesion", API_VERSION)
                        .addHeader("Authorization", "Y2FyYm9ib28yMDE1MDcwNV90ZXN0")
                        .addHeader("timestamp",time);
                if(deviceId!=null)
                    builder.addHeader("clientImei",deviceId);
                if(userId!=null)
                    builder.addHeader("userId",userId);
                if(sign!=null)
                    builder.addHeader("sign",sign);
                return chain.proceed(builder.build());
            }
        };
        OkHttpClient.Builder mHttpClientBuilder = new OkHttpClient.Builder();
        mHttpClientBuilder.addInterceptor(headerInterceptor);

        //Http日志截断器
        if(Config.LOG_LEVEL == 6) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            mHttpClientBuilder.addInterceptor(loggingInterceptor);
        }

        OkHttpClient client = mHttpClientBuilder.build();
        Retrofit retrofit = mRetrofitBuilder.client(client).build();
        return retrofit.create(serviceClass);
    }

    /**
     * 创建头部签名和服务器匹配
     * @param userId
     * @param timestamp
     * @param token
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String createSign(String userId,String timestamp, String token) {
        StringBuffer temp = new StringBuffer();
        if(null != userId && !"".equals(userId)){
            temp.append("userId="+userId).append("&");
        }
        if(null != timestamp && !"".equals(timestamp)){
            temp.append("timestamp="+timestamp).append("&");
        }
        if(null != token && !"".equals(token)){
            temp.append("token="+token);
        }
        try {
            return MD5.md5(temp.toString()).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
