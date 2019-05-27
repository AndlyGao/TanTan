package com.xuyijie.module_circle.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuyijie.module_circle.R;
import com.xuyijie.module_lib.base.BaseView;
import com.xuyijie.module_lib.gson.UserGson;
import com.xuyijie.module_lib.util.GlideUtil;

import java.util.List;

public class HotUserAdapter extends BaseQuickAdapter<UserGson, BaseViewHolder> {


    public HotUserAdapter(@Nullable List<UserGson> data) {
        super(R.layout.circle_ry_host_user_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserGson item) {
        if (item.getSex().equals("1")){
            GlideUtil.loadGeneralImage(R.mipmap.ic_boy, (ImageView) helper.getView(R.id.iv_sex));
        }else {
            GlideUtil.loadGeneralImage(R.mipmap.ic_girl, (ImageView) helper.getView(R.id.iv_sex));
        }
        GlideUtil.loadGeneralImage(item.getAvatar(), (ImageView) helper.getView(R.id.iv_avatar));
        helper.setText(R.id.tv_age, item.getAge() + "岁")
                .setText(R.id.tv_major, item.getMajor());
    }
}
