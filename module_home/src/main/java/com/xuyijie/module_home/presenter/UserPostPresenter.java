package com.xuyijie.module_home.presenter;

import com.xuyijie.module_home.contract.UserPostContract;
import com.xuyijie.module_home.model.UserPostModel;
import com.xuyijie.module_lib.base.BaseGson;
import com.xuyijie.module_lib.base.BasePresenter;
import com.xuyijie.module_lib.gson.UserPostGson;
import com.xuyijie.module_lib.http.BaseObserver;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserPostPresenter extends BasePresenter<UserPostContract.View> implements UserPostContract.Presenter {
    public UserPostPresenter(UserPostContract.View mMvpView) {
        super(mMvpView);
    }

    private UserPostModel userPostModel = new UserPostModel();

    @Override
    public void queryUserPostByPage(String page, String userId) {
        userPostModel.queryUserPostByPage(page, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<UserPostGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<UserPostGson> userPostGsonBaseGson) {
                        mMvpView.queryUserPostByPage(userPostGsonBaseGson.getData());
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
    }
}
