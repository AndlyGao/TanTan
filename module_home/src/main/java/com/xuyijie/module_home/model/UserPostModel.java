package com.xuyijie.module_home.model;

import com.xuyijie.module_home.contract.UserPostContract;
import com.xuyijie.module_lib.base.BaseGson;
import com.xuyijie.module_lib.gson.UserPostGson;
import com.xuyijie.module_lib.http.RetrofitUtils;

import rx.Observable;

public class UserPostModel implements UserPostContract.Model {
    @Override
    public Observable<BaseGson<UserPostGson>> queryUserPostByPage(String page, String userId) {
        return RetrofitUtils.getInstance().create().queryUserPostByPage(page,userId);
    }
}
