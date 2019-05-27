package com.xuyijie.module_lib.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuyijie.module_lib.App;
import com.xuyijie.module_lib.R;
import com.xuyijie.module_lib.gson.UserPostGson;
import com.xuyijie.module_lib.util.GlideUtil;

import java.util.List;

public class UserPostAdapter extends BaseQuickAdapter<UserPostGson, BaseViewHolder> {
    private Context context;

    public UserPostAdapter(@Nullable List<UserPostGson> data, Context context) {
        super(R.layout.ry_user_post_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, UserPostGson item) {
        GlideUtil.loadGeneralImage(item.getUser().getAvatar(), (ImageView) helper.getView(R.id.iv_avatar));
        helper.setText(R.id.tv_username, item.getUser().getNickname())
                .setText(R.id.tv_title, "#" + item.getPostTopic())
                .setText(R.id.tv_content, item.getPostContent());
        if (item.getUser().getSex().equals("1")) {
            GlideUtil.loadGeneralImage(R.mipmap.user_ic_16_male, (ImageView) helper.getView(R.id.iv_sex));
        } else {
            GlideUtil.loadGeneralImage(R.mipmap.user_ic_16_female, (ImageView) helper.getView(R.id.iv_sex));
        }
        RecyclerView ryPicture = helper.getView(R.id.ry_picture);
        int size = item.getPictures().size();
        if (size <= 4 && size > 1) {
            ryPicture.setLayoutManager(new GridLayoutManager(context, 2));
        } else if (size > 4) {
            ryPicture.setLayoutManager(new GridLayoutManager(context, 3));
        } else {
            ryPicture.setLayoutManager(new GridLayoutManager(context, 1));
        }
        UserPostPictureAdapter userPostPictureAdapter = new UserPostPictureAdapter(item.getPictures());
        ryPicture.setAdapter(userPostPictureAdapter);
    }

    private class UserPostPictureAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public UserPostPictureAdapter(@Nullable List<String> data) {
            super(R.layout.ry_user_post_picture_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            RoundedCorners roundedCorners = new RoundedCorners(10);
            RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300).centerCrop();
            Glide.with(App.getInstance()).asBitmap().apply(options).load(item).into((ImageView) helper.getView(R.id.iv_picture));
        }
    }
}
