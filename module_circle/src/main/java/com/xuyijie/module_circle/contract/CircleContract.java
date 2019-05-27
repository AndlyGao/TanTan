package com.xuyijie.module_circle.contract;

import com.xuyijie.module_lib.base.BaseGson;
import com.xuyijie.module_lib.base.BaseView;
import com.xuyijie.module_lib.gson.UserGson;

import java.util.List;

import retrofit2.http.Query;
import rx.Observable;

public interface CircleContract {
    interface Model {
        Observable<BaseGson<UserGson>>queryHotUserList( String latitude,String longitude);
    }

    interface View extends BaseView {
        void queryHotUserList(List<UserGson> list);
    }

    interface Presenter {
        void queryHotUserList( String latitude,String longitude);
    }
}
