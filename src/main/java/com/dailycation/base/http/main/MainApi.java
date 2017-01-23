package com.dailycation.base.http.main;


import java.util.HashMap;
import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by hehu on 17-1-5.
 */

public class MainApi extends BaseApi{
    public static Observable<MainResp> requestMainData(long clazzId,Teacher teacher,String versionName,int versionCode) {
        HashMap params = new HashMap();
        try{
            InfoRecord infoRecord = teacher.getInfoRecord();
            params.put("readedSuggestionMaxId", infoRecord.getFeedReadMax());
            params.put("readedSchoolNoticeMaxId", infoRecord.getClazzReadRecords().get(clazzId).getAnnounceReadMax());
            params.put("readedLeaveMaxId", infoRecord.getClazzReadRecords().get(clazzId).getLeaveReadMax());
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            MainService api = createService(MainService.class);
            params.put("userId", teacher.getUserId());
            params.put("defaultClassId", clazzId);
            params.put("version", versionName);
            params.put("deviceType", Config.DEVICE_TYPE_ANDROID);
            return api.requestMainData(params);
        }catch (NullPointerException e){
            return Observable.create(new RequestParamNullOnSubscribe());
        }
    }

    public static Observable<MainResp> requestSwitchClazz(Teacher teacher,long clazzId) {
        try {
            MainService api = createService(MainService.class);
            HashMap params = new HashMap();
            params.put("userId", teacher.getUserId());
            params.put("defaultClassId", clazzId);
            return api.requestSwitchClazz(params);
        }catch (NullPointerException e){
            return Observable.create(new RequestParamNullOnSubscribe());
        }
    }

    public static Observable<BaseResp> registerXG(Teacher teacher,String token){
        try {
            MainService api = createService(MainService.class);
            HashMap<String,Object> params = new HashMap<>();
            params.put("userId", teacher.getUserId());
            params.put("deviceToken", token);
            params.put("deviceType", 1);
            return api.registerXG(params);
        }catch (NullPointerException e){
            return Observable.create(new RequestParamNullOnSubscribe());
        }
    }

    private interface MainService {
        @POST("teacher/index")
        Observable<MainResp> requestMainData(@Body Map params);

        @POST("teacher/changeClass")
        Observable<MainResp> requestSwitchClazz(@Body Map params);

        @POST("teacher/logDeviceToken")
        Observable<BaseResp> registerXG(@Body Map params);
    }
}
