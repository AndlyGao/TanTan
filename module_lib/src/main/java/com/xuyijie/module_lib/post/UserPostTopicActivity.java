package com.xuyijie.module_lib.post;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xuyijie.module_lib.R;
import com.xuyijie.module_lib.R2;
import com.xuyijie.module_lib.adapter.HotTopicAdapter;
import com.xuyijie.module_lib.base.BaseActivity;
import com.xuyijie.module_lib.contract.UserTopicContract;
import com.xuyijie.module_lib.gson.UserTopicGson;
import com.xuyijie.module_lib.presenter.UserTopicPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserPostTopicActivity extends BaseActivity<UserTopicContract.View, UserTopicPresenter> implements UserTopicContract.View {

    @BindView(R2.id.iv_close)
    ImageView ivClose;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_menu)
    TextView tvMenu;
    @BindView(R2.id.tb_common)
    RelativeLayout tbCommon;
    @BindView(R2.id.et_search)
    EditText etSearch;
    @BindView(R2.id.tv_search)
    TextView tvSearch;
    @BindView(R2.id.ry_hot_topic)
    RecyclerView ryHotTopic;
    private HotTopicAdapter hotTopicAdapter;

    @Override
    public boolean isSetStatusBarTranslucent() {
        return false;
    }

    @Override
    public UserTopicPresenter getPresenter() {
        return new UserTopicPresenter(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_user_post_topic;
    }

    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        initToolBar();
        hotTopicAdapter = new HotTopicAdapter(null, UserPostTopicActivity.this);
        ryHotTopic.setLayoutManager(new LinearLayoutManager(UserPostTopicActivity.this));
        ryHotTopic.setAdapter(hotTopicAdapter);
        hideKeyboard(etSearch);
        hotTopicAdapter.setItemClickListener(new HotTopicAdapter.onClickListener() {
            @Override
            public void onItemClick(UserTopicGson userTopicGson) {
                Log.i(TAG, "onItemClick: "+userTopicGson.getTopicName());
                Intent intent = new Intent(UserPostTopicActivity.this, UserPicturePostActivity.class);
                intent.putExtra("topicName",userTopicGson.getTopicName());
                intent.putExtra("topicId",userTopicGson.getId());
                setResult(3, intent);
                finish();
            }
        });
    }

    @Override
    public void initData() {
        mPresenter.queryUserPostTopic("");
    }

    @Override
    public void queryUserPostTopic(List<UserTopicGson> userTopicGsons) {
        hotTopicAdapter.replaceData(userTopicGsons);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }
}
