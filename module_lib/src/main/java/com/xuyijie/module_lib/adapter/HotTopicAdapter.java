package com.xuyijie.module_lib.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuyijie.module_lib.R;
import com.xuyijie.module_lib.gson.UserTopicGson;
import com.xuyijie.module_lib.post.UserPicturePostActivity;

import java.util.List;

public class HotTopicAdapter extends BaseQuickAdapter<UserTopicGson, BaseViewHolder> {
    private Context context;

    public HotTopicAdapter(@Nullable List<UserTopicGson> data, Context context) {
        super(R.layout.lib_hot_topic_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final UserTopicGson item) {
        helper.setText(R.id.tv_item, item.getTopicName())
                .setOnClickListener(R.id.tv_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(item);
                    }
                });
    }

    public void setItemClickListener(HotTopicAdapter.onClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private onClickListener onItemClickListener;

    public interface onClickListener {
        void onItemClick(UserTopicGson userTopicGson);
    }
}
