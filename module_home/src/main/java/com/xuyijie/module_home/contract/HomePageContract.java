package com.xuyijie.module_home.contract;

import com.xuyijie.module_lib.base.BaseGson;
import com.xuyijie.module_lib.base.BaseView;
import com.xuyijie.module_lib.gson.HomeTitleGson;

import java.util.List;

import rx.Observable;

public interface HomePageContract {
    interface Model {
        Observable<BaseGson<HomeTitleGson>> queryHomeActiveName();
    }

    interface View extends BaseView {
        void queryHomeActiveName(List<HomeTitleGson> homeTitleGsons);
    }

    interface Presenter {
        void queryHomeActiveName();
    }
}
