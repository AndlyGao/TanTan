package com.xuyijie.module_lib.contract;

import com.xuyijie.module_lib.base.BaseGson;
import com.xuyijie.module_lib.base.BaseView;
import com.xuyijie.module_lib.gson.UserTopicGson;

import java.util.List;

import rx.Observable;

public interface UserTopicContract {
    interface Model {
        Observable<BaseGson<UserTopicGson>> queryUserPostTopic(String userId);
    }

    interface View extends BaseView {
        void queryUserPostTopic(List<UserTopicGson> userTopicGsons);
    }

    interface Presenter {
        void queryUserPostTopic(String userId);
    }
}
