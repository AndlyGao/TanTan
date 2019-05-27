package com.xuyijie.module_home.presenter;

import com.xuyijie.module_home.contract.HomePageContract;
import com.xuyijie.module_home.model.HomePageModel;
import com.xuyijie.module_lib.base.BaseGson;
import com.xuyijie.module_lib.base.BasePresenter;
import com.xuyijie.module_lib.gson.HomeTitleGson;
import com.xuyijie.module_lib.http.BaseObserver;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class HomePagePresenter extends BasePresenter<HomePageContract.View> implements HomePageContract.Presenter {

    public HomePagePresenter(HomePageContract.View mMvpView) {
        super(mMvpView);
    }

    private HomePageModel pageModel = new HomePageModel();

    @Override
    public void queryHomeActiveName() {
        pageModel.queryHomeActiveName()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<HomeTitleGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<HomeTitleGson> homeTitleGsonBaseGson) {
                        mMvpView.queryHomeActiveName(homeTitleGsonBaseGson.getData());
                    }

                    @Override
                    public void onError(String error) {


                    }
                });
    }
}
