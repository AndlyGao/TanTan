package com.xuyijie.module_home.model;

import com.xuyijie.module_home.contract.HomePageContract;
import com.xuyijie.module_lib.base.BaseGson;
import com.xuyijie.module_lib.gson.HomeTitleGson;
import com.xuyijie.module_lib.http.RetrofitUtils;

import rx.Observable;

public class HomePageModel implements HomePageContract.Model {
    @Override
    public Observable<BaseGson<HomeTitleGson>> queryHomeActiveName() {
        return RetrofitUtils.getInstance().create().queryHomeActiveName();
    }
}
