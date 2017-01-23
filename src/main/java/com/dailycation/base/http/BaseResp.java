package com.dailycation.base.http;


import com.google.gson.Gson;
import com.google.gson.JsonElement;

/**
 * Created by hehu on 16-12-23.
 */

public class BaseResp extends Resp {
    transient protected Gson gson;
    JsonElement data;

    public BaseResp() {
        gson = new Gson();
    }

    public JsonElement getData() {
        return data;
    }

    @Override
    public String toString() {
        return "BaseResp{" +
                "gson=" + gson +
                ", data=" + data +
                "} " + super.toString();
    }
}
