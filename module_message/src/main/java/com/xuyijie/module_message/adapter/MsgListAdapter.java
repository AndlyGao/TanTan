package com.xuyijie.module_message.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuyijie.module_lib.App;
import com.xuyijie.module_lib.util.DateUtils;
import com.xuyijie.module_lib.util.GlideUtil;
import com.xuyijie.module_message.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.android.api.options.MessageSendingOptions;

import static com.xuyijie.module_lib.config.ArouterConfig.CHAT_PAGE;

public class MsgListAdapter extends BaseQuickAdapter<Conversation, BaseViewHolder> {
    private Context context;

    public MsgListAdapter(@Nullable List<Conversation> data, Context context) {
        super(R.layout.ry_user_msg_list_item, data);
        this.context = context;
    }

    public interface onItemLondClickListener {
        void onItemLongClick(String username);
    }

    public void setOnItemLondClickListener(MsgListAdapter.onItemLondClickListener onItemLondClickListener) {
        this.onItemLondClickListener = onItemLondClickListener;
    }

    private onItemLondClickListener onItemLondClickListener;

    @Override
    protected void convert(BaseViewHolder helper, final Conversation item) {
        Message latestMessage = item.getLatestMessage();
        Log.i(TAG, "convert: " + item.getLatestMessage().haveRead());
        final UserInfo targetInfo = (UserInfo) latestMessage.getTargetInfo();
        TextContent content = (TextContent) latestMessage.getContent();
        helper.setText(R.id.tv_username, targetInfo.getNickname())
                .setText(R.id.tv_msg, content.getText())
                .setText(R.id.tv_time, DateUtils.format(item.getLatestMessage().getCreateTime()))
                .setOnClickListener(R.id.ll_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        item.resetUnreadCount();
                        ARouter.getInstance().build(CHAT_PAGE).withString("username", targetInfo.getUserName()).withString("nickname", targetInfo.getNickname()).navigation(context);
                    }
                })
                .setOnLongClickListener(R.id.ll_item, new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Log.i(TAG, "onLongClick: " + targetInfo.getUserName());
                        onItemLondClickListener.onItemLongClick(targetInfo.getUserName());
                        return true;
                    }
                });
        int unReadMsgCnt = item.getUnReadMsgCnt();
        UserInfo.Gender gender = ((UserInfo) item.getTargetInfo()).getGender();
        if (UserInfo.Gender.female == gender) {
            GlideUtil.loadGeneralImage(R.mipmap.ic_conversation_famale, (ImageView) helper.getView(R.id.iv_sex));
        } else {
            GlideUtil.loadGeneralImage(R.mipmap.ic_conversation_male, (ImageView) helper.getView(R.id.iv_sex));
        }
        TextView view = helper.getView(R.id.tv_unread);
        Log.i(TAG, "convert: " + unReadMsgCnt);
        if (unReadMsgCnt > 0) {
            if (unReadMsgCnt < 100) {
                view.setVisibility(View.VISIBLE);
                view.setText(unReadMsgCnt + "");
            } else {
                view.setVisibility(View.VISIBLE);
                view.setText("99");
            }
        } else {
            view.setVisibility(View.GONE);
        }
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.drawable.t7_share_zhenjing_tab);
        Glide.with(App.getInstance()).asBitmap().apply(requestOptions).load(targetInfo.getAvatarFile()).into((ImageView) helper.getView(R.id.iv_avatar));


    }


}
