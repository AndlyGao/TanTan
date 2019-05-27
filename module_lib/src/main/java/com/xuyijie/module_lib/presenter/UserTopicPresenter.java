package com.xuyijie.module_lib.presenter;

import com.xuyijie.module_lib.base.BaseGson;
import com.xuyijie.module_lib.base.BasePresenter;
import com.xuyijie.module_lib.contract.UserTopicContract;
import com.xuyijie.module_lib.gson.UserPostGson;
import com.xuyijie.module_lib.gson.UserTopicGson;
import com.xuyijie.module_lib.http.BaseObserver;
import com.xuyijie.module_lib.model.UserTopicModel;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserTopicPresenter extends BasePresenter<UserTopicContract.View> implements UserTopicContract.Presenter {

    public UserTopicPresenter(UserTopicContract.View mMvpView) {
        super(mMvpView);
    }

    private UserTopicModel userTopicModel = new UserTopicModel();

    @Override
    public void queryUserPostTopic(String userId) {
        userTopicModel.queryUserPostTopic(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<UserTopicGson>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<UserTopicGson> userPostGsonBaseGson) {
                        mMvpView.queryUserPostTopic(userPostGsonBaseGson.getData());
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
    }
}
