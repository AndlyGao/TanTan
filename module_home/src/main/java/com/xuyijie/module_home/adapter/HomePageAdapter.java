package com.xuyijie.module_home.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuyijie.module_home.R;
import com.xuyijie.module_lib.gson.HomeTitleGson;
import com.xuyijie.module_lib.util.GlideUtil;
import com.xuyijie.module_lib.view.CustomRoundAngleImageView;

import java.util.List;

public class HomePageAdapter extends BaseQuickAdapter<HomeTitleGson, BaseViewHolder> {
    public HomePageAdapter(@Nullable List<HomeTitleGson> data) {
        super(R.layout.item_home_title, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeTitleGson item) {
        helper.setText(R.id.tv_name, item.getActiveName());
        ImageView view = (ImageView) helper.getView(R.id.iv_icon);
        GlideUtil.loadRoundCorner(view,150,item.getActivePicture() );
    }
}
