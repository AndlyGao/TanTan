package com.xuyijie.module_lib.model;

import com.xuyijie.module_lib.base.BaseGson;
import com.xuyijie.module_lib.contract.UserTopicContract;
import com.xuyijie.module_lib.gson.UserTopicGson;
import com.xuyijie.module_lib.http.RetrofitUtils;

import rx.Observable;

public class UserTopicModel implements UserTopicContract.Model {
    @Override
    public Observable<BaseGson<UserTopicGson>> queryUserPostTopic(String userId) {
        return RetrofitUtils.getInstance().create().queryUserPostTopic(userId);
    }
}
