package com.dailycation.base.http.main;


import java.util.Arrays;
import java.util.List;

/**
 * Created by hehu on 17-1-5.
 */

public class MainResp extends BaseResp {

    public Attendance getCurrentClazzAtt() {
        return gson.fromJson(getData(),Attendance.class);
    }

    public List<Clazz> getClazzs() {
        return Arrays.asList(gson.fromJson(getData().getAsJsonObject().get("classList"),Clazz[].class));
    }

    public InfoRecord getInfoRecord() {
        return gson.fromJson(getData(),InfoRecord.class);
    }

    public ReadRecord getReadRecord() {
        return gson.fromJson(getData(),ReadRecord.class);
    }

    public long getDefaultClazzId(){
        return getData().getAsJsonObject().get("defaultClassId").getAsLong();
    }
}
