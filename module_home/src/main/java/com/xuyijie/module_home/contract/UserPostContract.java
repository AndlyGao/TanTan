package com.xuyijie.module_home.contract;

import com.xuyijie.module_lib.base.BaseGson;
import com.xuyijie.module_lib.base.BaseView;
import com.xuyijie.module_lib.gson.UserPostGson;

import java.util.List;

import rx.Observable;

public interface UserPostContract {
    interface Model {
        Observable<BaseGson<UserPostGson>> queryUserPostByPage(String page,String userId);
    }

    interface View extends BaseView {
      void   queryUserPostByPage(List<UserPostGson> userPostGsonList);
    }

    interface Presenter {
        void queryUserPostByPage(String page,String userId);

    }
}
