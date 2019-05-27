package com.xuyijie.module_lib.presenter;

import com.xuyijie.module_lib.base.BasePresenter;
import com.xuyijie.module_lib.contract.EmptyContract;

public class EmptyPresenter extends BasePresenter<EmptyContract.View> implements EmptyContract.Presenter {

    public EmptyPresenter(EmptyContract.View mMvpView) {
        super(mMvpView);
    }
}
