package com.xuyijie.module_message;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.xuyijie.module_lib.App;
import com.xuyijie.module_lib.base.BaseFragment;
import com.xuyijie.module_lib.entity.MessageWrap;
import com.xuyijie.module_lib.presenter.EmptyPresenter;
import com.xuyijie.module_lib.util.IMUtils;
import com.xuyijie.module_lib.util.ToImg3;
import com.xuyijie.module_lib.view.toast.ToastUtils;
import com.xuyijie.module_message.adapter.MsgListAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.ContactNotifyEvent;
import cn.jpush.im.android.api.event.ConversationRefreshEvent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.MessageReceiptStatusChangeEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

public class MessageFragment extends BaseFragment<EmptyPresenter> {
    @BindView(R2.id.ry_msg_list)
    RecyclerView ryMsgList;
    Unbinder unbinder;
    private MsgListAdapter msgListAdapter;

    @Override
    public void initData() {
        JMessageClient.registerEventReceiver(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        JMessageClient.unRegisterEventReceiver(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)      //标记和发送消息的标记一样的,包括类型和值都必须一样
    public void onRecieve(MessageWrap result) {//这个参数是传递过来的数据   类型亦必须一样

    }
    private static final String TAG = "MessageFragment";

    @Override
    public void initView(View rootView, ViewGroup container,Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);

        List<Conversation> conversationList = JMessageClient.getConversationList();
        Log.i(TAG, "gotResult: " + conversationList.size());
        msgListAdapter = new MsgListAdapter(conversationList, getContext());
        ryMsgList.setLayoutManager(new LinearLayoutManager(getContext()));
        ryMsgList.setNestedScrollingEnabled(false);
        ryMsgList.setAdapter(msgListAdapter);
        msgListAdapter.setOnItemLondClickListener(new MsgListAdapter.onItemLondClickListener() {
            @Override
            public void onItemLongClick(final String username) {
                Log.i(TAG, "onItemLongClick: " + username);
                new AlertView.Builder().setContext(getContext())
                        .setStyle(AlertView.Style.Alert)
                        .setTitle("删除会话")
                        .setMessage("是否删除当前会话？")
                        .setCancelText("取消")
                        .setDestructive("确定")
                        .setOthers(null)
                        .setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                                Log.i(TAG, "onItemClick: " + position);
                                if (position == 0) {
                                    JMessageClient.deleteSingleConversation(username);
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            android.os.Message message = new android.os.Message();
                                            message.what = 0;
                                            handler.sendMessage(message);
                                        }
                                    }).start();
                                }

                            }
                        })
                        .build()
                        .show();
            }
        });
    }

    public void onEvent(final MessageEvent event) {
        int allUnReadMsgCount = JMessageClient.getAllUnReadMsgCount();
        EventBus.getDefault().post(MessageWrap.getInstance("11"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                android.os.Message message = new android.os.Message();
                message.what = 0;
                handler.sendMessage(message);

            }
        }).start();
    }

    @Override
    public void onResume() {
        super.onResume();
        JMessageClient.login("1234567", "1234567", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i == 0) {
                    List<Conversation> conversationList = JMessageClient.getConversationList();
                    Log.i(TAG, "gotResult: " + conversationList.size());
                    msgListAdapter.setNewData(conversationList);
                    msgListAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    List<Conversation> conversationList = JMessageClient.getConversationList();
                    Log.i(TAG, "gotResult: " + conversationList.size());
                    msgListAdapter.setNewData(conversationList);
                    msgListAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };


    @Override
    public int initLayout() {
        return R.layout.fragment_message;
    }

    @Override
    public EmptyPresenter initPresenter() {
        return null;
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
}
