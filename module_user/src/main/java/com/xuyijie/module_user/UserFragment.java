package com.xuyijie.module_user;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.xuyijie.module_lib.base.BaseFragment;
import com.xuyijie.module_lib.presenter.EmptyPresenter;

public class UserFragment extends BaseFragment<EmptyPresenter> {
    @Override
    public void initData() {

    }

    @Override
    public void initView(View view, ViewGroup container, Bundle savedInstanceState) {

    }

    @Override
    public int initLayout() {
        return R.layout.fragment_user;
    }

    @Override
    public EmptyPresenter initPresenter() {
        return null;
    }
}
