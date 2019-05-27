package com.xuyijie.module_lib.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.xuyijie.module_lib.R;
import com.xuyijie.module_lib.R2;
import com.xuyijie.module_lib.util.DateUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.MessageStatus;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.api.BasicCallback;

public class MessageDetailAdapter extends RecyclerView.Adapter<MessageDetailAdapter.ViewHolderReceipt> {
    private Context context;
    private List<Message> items;
    private ClickListener clickListener;

    public MessageDetailAdapter(Context context, List<Message> items) {
        this.context = context;
        this.items = items;
    }

    public interface ClickListener {
        void onItemClick(View v, int position, List<Message> items);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolderReceipt onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        ViewHolderReceipt viewHolder = null;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_accept, parent, false);
                viewHolder = new ViewHolderReceipt(view);
                break;
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_send, parent, false);
                viewHolder = new ViewHolderReceipt(view);
                break;
        }
        return viewHolder;
    }

    private static final String TAG = "MessageDetailAdapter";

    @Override
    public int getItemViewType(int position) {
        String userName = items.get(position).getFromUser().getUserName();
        Log.i(TAG, "getItemViewType: " + ((TextContent) items.get(position).getContent()).getText());
        if (userName.equals("1234567")) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolderReceipt holder, int position) {
        Message messageBean = items.get(position);
        if (messageBean.getFromUser().getUserName().equals("1234567")){
            if (messageBean.haveRead()) {
                holder.tvRead.setText("已读");
                holder.tvRead.setTextColor(0xff1296db);
            } else {
                holder.tvRead.setText("未读");
                holder.tvRead.setTextColor(0xffbfbfbf);
            }
        }else {
            if (messageBean.haveRead()) {
                holder.tvRead.setText("已读");
                holder.tvRead.setTextColor(0xff1296db);
            } else {
                holder.tvRead.setText("未读");
                holder.tvRead.setTextColor(0xffbfbfbf);
            }
        }
        ViewHolderReceipt viewHolder = (ViewHolderReceipt) holder;
        viewHolder.mItemMsg.setText(((TextContent) messageBean.getContent()).getText());
        viewHolder.itemUserId.setText(DateUtils.format(messageBean.getCreateTime()));
        MessageStatus status = messageBean.getStatus();
        if (status == MessageStatus.send_going) {
            viewHolder.chatItemProgress.setVisibility(View.VISIBLE);
        } else if (status == MessageStatus.send_success) {
            viewHolder.chatItemProgress.setVisibility(View.GONE);
        }
        messageBean.setHaveRead(new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i == 0) {
                    Log.i(TAG, "gotResult: " + "已读");
                } else {
                    Log.i(TAG, "gotResult: " + "已读失败" + s);
                }
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getLayoutPosition();
                clickListener.onItemClick(holder.itemView, pos, items);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (items != null) {
            return items.size();
        }
        return 0;
    }

    /**
     * ViewHolderReceipt
     */
    public static class ViewHolderReceipt extends RecyclerView.ViewHolder {
        @BindView(R2.id.item_msg)
        TextView mItemMsg;
        @BindView(R2.id.item_user_id)
        TextView itemUserId;
        @BindView(R2.id.tv_read)
        TextView tvRead;
        @BindView(R2.id.head_img)
        ImageView mHeadImg;
        @BindView(R2.id.chat_item_progress)
        ProgressBar chatItemProgress;

        public ViewHolderReceipt(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    public static class ViewHolderSend extends RecyclerView.ViewHolder {
        @BindView(R2.id.item_msg)
        TextView mItemMsg;
        @BindView(R2.id.item_user_id)
        TextView itemUserId;
        @BindView(R2.id.tv_read)
        TextView tvRead;
        @BindView(R2.id.head_img)
        ImageView mHeadImg;
        @BindView(R2.id.chat_item_progress)
        ProgressBar chatItemProgress;

        public ViewHolderSend(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}