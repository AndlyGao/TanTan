package com.xuyijie.module_lib.conversation;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xuyijie.module_lib.R;
import com.xuyijie.module_lib.R2;
import com.xuyijie.module_lib.adapter.MessageDetailAdapter;
import com.xuyijie.module_lib.base.BaseActivity;
import com.xuyijie.module_lib.contract.EmptyContract;
import com.xuyijie.module_lib.entity.UserStateBean;
import com.xuyijie.module_lib.presenter.EmptyPresenter;
import com.xuyijie.module_lib.util.GlideUtil;
import com.xuyijie.module_lib.util.IMUtils;
import com.xuyijie.module_lib.util.NetWorkManager;
import com.xuyijie.module_lib.util.UiUtil;
import com.xuyijie.module_lib.view.toast.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.DeviceInfo;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.android.api.options.MessageSendingOptions;
import cn.jpush.im.api.BasicCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.xuyijie.module_lib.config.ArouterConfig.CHAT_PAGE;

@Route(path = CHAT_PAGE)
public class SingleConversationActivity extends BaseActivity<EmptyContract.View, EmptyPresenter> implements View.OnClickListener {


    @BindView(R2.id.btn_message_send)
    Button mBtnSend;
    @BindView(R2.id.tv_line)
    TextView tvLine;
    @BindView(R2.id.iv_line)
    ImageView ivLine;

    @BindView(R2.id.et_message_input)
    EditText mMsgInput;

    @BindView(R2.id.message_detail_recycle_list)
    RecyclerView mRecyclerView;

    private MessageDetailAdapter mAdapter;
    private List<Message> oldestMessage = new ArrayList<>();


    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public EmptyPresenter getPresenter() {
        return null;
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_single_conversation;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JMessageClient.unRegisterEventReceiver(this);
    }


    private void getUserState() {

        NetWorkManager.isFriendState(getIntent().getStringExtra("username"), new Callback<UserStateBean>() {

            @Override
            public void onResponse(Call<UserStateBean> call, Response<UserStateBean> response) {
                Log.i(TAG, "onResponse: " + response.body());
                if (response.code() == 200) {
                    if (response.body().online) {
                        GlideUtil.loadGeneralImage(R.mipmap.ic_online, ivLine);
                        tvLine.setText("[在线]");
                    } else {
                        GlideUtil.loadGeneralImage(R.mipmap.ic_offline, ivLine);
                        tvLine.setText("[离线]");
                    }
                } else {
                    GlideUtil.loadGeneralImage(R.mipmap.ic_offline, ivLine);
                    tvLine.setText("[未知]");
                }
            }

            @Override
            public void onFailure(Call<UserStateBean> call, Throwable throwable) {
                GlideUtil.loadGeneralImage(R.mipmap.ic_offline, ivLine);
                tvLine.setText("[未知]");
            }
        });
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        JMessageClient.registerEventReceiver(this);
        mBtnSend.setOnClickListener(this);
        initToolBar().setToolBarTitle(getIntent().getStringExtra("nickname"));
        LinearLayoutManager layoutManager = new LinearLayoutManager(SingleConversationActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);


        oldestMessage = genData();
        mAdapter = new MessageDetailAdapter(SingleConversationActivity.this, oldestMessage);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MessageDetailAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position, List<Message> items) {

            }
        });
    }

    public void onEventMainThread(final MessageEvent event) {
        //do your own business
        oldestMessage.add(event.getMessage());
        event.getMessage().setHaveRead(new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i == 0) {

                }
            }
        });
        Log.i(TAG, "onEventMainThread: ");
        android.os.Message message1 = new android.os.Message();
        message1.what = 0;
        handler.sendMessage(message1);
    }

    private List<Message> genData() {
        JMessageClient.login("1234567", "1234567", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i == 0) {
                    Conversation singleConversation = JMessageClient.getSingleConversation(getIntent().getStringExtra("username"), "dae8e686ae56813e639f1200");
                    List<Message> allMessage = singleConversation.getAllMessage();
                    Log.i(TAG, "gotResult: " + allMessage.size());
                    MessageSendingOptions messageSendingOptions = new MessageSendingOptions();
                    messageSendingOptions.setNeedReadReceipt(true);
                    singleConversation.resetUnreadCount();
                    oldestMessage.addAll(allMessage);
                    mRecyclerView.scrollToPosition(oldestMessage.size() - 1);
                    getUserState();
                } else {
                    ToastUtils.show("登陆出错");
                }
            }
        });
        return oldestMessage;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();//返回
        if (i == R.id.iv_close) {
            finish();

        } else if (i == R.id.btn_message_send) {
            String inputContent = mMsgInput.getText().toString();
            if (!TextUtils.isEmpty(inputContent)) {
                Conversation singleConversation = Conversation.createSingleConversation(getIntent().getStringExtra("username"), "dae8e686ae56813e639f1200");
                if (singleConversation != null) {
                    final Message message = IMUtils.sendTextMessage(singleConversation, mMsgInput.getText().toString());
                    JMessageClient.sendMessage(message);
                    oldestMessage.add(message);
                    message.setOnSendCompleteCallback(new BasicCallback() {
                        @Override
                        public void gotResult(int code, String s) {
                            if (code == IMUtils.CODE_SUCCESS) {
                                Log.i(TAG, "gotResult: 发送成功");
                                android.os.Message message1 = new android.os.Message();
                                message1.what = 0;

                                handler.sendMessage(message1);
                            } else {
                                ToastUtils.show("发送失败,错误信息：" + s + "错误代码：" + code);
                            }
                        }
                    });
                }
                mAdapter.notifyDataSetChanged();
                mMsgInput.clearFocus();
                mMsgInput.setSelected(false);
                mMsgInput.setText("");
                UiUtil.hideSoftKeyboard(SingleConversationActivity.this, mMsgInput);
            }
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.scrollToPosition(oldestMessage.size() - 1);
                    break;
            }
        }
    };


    @Override
    public void initData() {

    }
}
