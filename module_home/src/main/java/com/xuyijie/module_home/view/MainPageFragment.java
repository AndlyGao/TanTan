package com.xuyijie.module_home.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuyijie.module_home.R;
import com.xuyijie.module_home.R2;
import com.xuyijie.module_home.contract.HomePageContract;
import com.xuyijie.module_home.contract.UserPostContract;
import com.xuyijie.module_home.presenter.HomePagePresenter;
import com.xuyijie.module_home.presenter.UserPostPresenter;
import com.xuyijie.module_lib.adapter.UserPostAdapter;
import com.xuyijie.module_lib.base.BaseFragment;
import com.xuyijie.module_lib.gson.HomeTitleGson;
import com.xuyijie.module_lib.gson.UserPostGson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainPageFragment extends BaseFragment<UserPostPresenter> implements UserPostContract.View {
    @BindView(R2.id.ry_purse)
    RecyclerView ryPurse;
    Unbinder unbinder;
    private UserPostAdapter userPostAdapter;

    @Override
    public void initData() {
        mPresenter.queryUserPostByPage("1", "1");

    }

    @Override
    public void initView(View view, ViewGroup container,Bundle savedInstanceState) {
        userPostAdapter = new UserPostAdapter(null,getContext());
        ryPurse.setLayoutManager(new LinearLayoutManager(getContext()));
        ryPurse.setAdapter(userPostAdapter);
    }

    @Override
    public int initLayout() {
        return R.layout.fragment_main_page;
    }

    @Override
    public UserPostPresenter initPresenter() {
        return new UserPostPresenter(this);
    }


    @Override
    public void showError(String msg) {

    }

    @Override
    public void showDialog() {

    }

    @Override
    public void hideDialog() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void queryUserPostByPage(List<UserPostGson> userPostGsonList) {
        userPostAdapter.replaceData(userPostGsonList);
    }
}
