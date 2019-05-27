package com.xuyijie.module_circle.presenter;

import com.xuyijie.module_circle.contract.CircleContract;
import com.xuyijie.module_circle.model.CircleModel;
import com.xuyijie.module_lib.base.BaseGson;
import com.xuyijie.module_lib.base.BasePresenter;
import com.xuyijie.module_lib.gson.UserGson;
import com.xuyijie.module_lib.http.BaseObserver;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CirclePresenter extends BasePresenter<CircleContract.View> implements CircleContract.Presenter {

    public CirclePresenter(CircleContract.View mMvpView) {
        super(mMvpView);
    }

    private CircleModel circleModel = new CircleModel();

    @Override
    public void queryHotUserList(String latitude, String longitude) {
        circleModel.queryHotUserList(latitude,longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<UserGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<UserGson> userGsonBaseGson) {
                        mMvpView.queryHotUserList(userGsonBaseGson.getData());
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
    }
}
