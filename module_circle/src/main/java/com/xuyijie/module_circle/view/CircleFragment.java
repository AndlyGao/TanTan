package com.xuyijie.module_circle.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.bumptech.glide.Glide;
import com.xuyijie.module_circle.R;
import com.xuyijie.module_circle.R2;
import com.xuyijie.module_circle.adapter.HotUserAdapter;
import com.xuyijie.module_circle.contract.CircleContract;
import com.xuyijie.module_circle.presenter.CirclePresenter;
import com.xuyijie.module_lib.base.BaseFragment;
import com.xuyijie.module_lib.gson.UserGson;
import com.xuyijie.module_lib.presenter.EmptyPresenter;
import com.xuyijie.module_lib.util.GlideUtil;
import com.xuyijie.module_lib.view.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CircleFragment extends BaseFragment<CirclePresenter> implements CircleContract.View{
    @BindView(R2.id.ry_hot_user)
    RecyclerView ryHotUser;
    Unbinder unbinder;
    private HotUserAdapter hotUserAdapter;


    @Override
    public void initData() {
        hotUserAdapter = new HotUserAdapter(null);
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        layout.setOrientation(LinearLayoutManager.HORIZONTAL);
        ryHotUser.setLayoutManager(layout);
        ryHotUser.setAdapter(hotUserAdapter);
        mPresenter.queryHotUserList("30.739584", "120.718603");
    }



    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;




    @Override
    public void initView(View rootView, ViewGroup container, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, rootView);


    }



    @Override
    public int initLayout() {
        return R.layout.fragment_circle;
    }

    @Override
    public CirclePresenter initPresenter() {
        return new CirclePresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void queryHotUserList(List<UserGson> list) {
        hotUserAdapter.replaceData(list);
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


}
