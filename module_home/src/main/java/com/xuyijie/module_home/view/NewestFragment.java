package com.xuyijie.module_home.view;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.xuyijie.module_home.R;
import com.xuyijie.module_lib.base.BaseFragment;
import com.xuyijie.module_lib.presenter.EmptyPresenter;

public class NewestFragment extends BaseFragment<EmptyPresenter> {
    @Override
    public void initData() {

    }

    @Override
    public void initView(View view, ViewGroup container, Bundle savedInstanceState) {

    }

    @Override
    public int initLayout() {
        return R.layout.fragment_newest;
    }

    @Override
    public EmptyPresenter initPresenter() {
        return null;
    }
}
