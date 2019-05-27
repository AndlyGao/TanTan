package com.xuyijie.module_circle.model;

import com.xuyijie.module_circle.contract.CircleContract;
import com.xuyijie.module_lib.base.BaseGson;
import com.xuyijie.module_lib.gson.UserGson;
import com.xuyijie.module_lib.http.RetrofitUtils;

import rx.Observable;

public class CircleModel implements CircleContract.Model {
    @Override
    public Observable<BaseGson<UserGson>> queryHotUserList(String latitude, String longitude) {
        return RetrofitUtils.getInstance().create().queryHotUserList(latitude,longitude);
    }
}
