package com.xuyijie.module_lib.post;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xuyijie.module_lib.R;
import com.xuyijie.module_lib.R2;
import com.xuyijie.module_lib.adapter.FullyGridLayoutManager;
import com.xuyijie.module_lib.adapter.GridImageAdapter;
import com.xuyijie.module_lib.base.BaseActivity;
import com.xuyijie.module_lib.contract.EmptyContract;
import com.xuyijie.module_lib.presenter.EmptyPresenter;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;
import com.yuyh.library.imgsel.config.ISListConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.util.V;

public class UserPicturePostActivity extends BaseActivity<EmptyContract.View, EmptyPresenter> {

    private static final int REQUEST_LIST_CODE = 0;
    private static final int REQUEST_CAMERA_CODE = 1;
    @BindView(R2.id.iv_close)
    ImageView ivClose;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_submit)
    TextView tvSubmit;
    @BindView(R2.id.tb_common)
    RelativeLayout tbCommon;
    @BindView(R2.id.et_content)
    EditText etContent;
    @BindView(R2.id.tv_count)
    TextView tvCount;
    @BindView(R2.id.fl_edit)
    FrameLayout flEdit;
    @BindView(R2.id.ry_picture)
    RecyclerView ryPicture;
    @BindView(R2.id.iv_picture)
    ImageView ivPicture;
    @BindView(R2.id.iv_ate)
    ImageView ivAte;
    @BindView(R2.id.iv_topic)
    ImageView ivTopic;
    @BindView(R2.id.tv_topic)
    TextView tvTopic;
    @BindView(R2.id.tv_describe)
    TextView tvDescribe;
    private List<String> selectList = new ArrayList<>();
    private GridImageAdapter adapter;

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
        return R.layout.activity_user_picture_post;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        initToolBar();
        ISNav.getInstance().init(new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });


        FullyGridLayoutManager manager = new FullyGridLayoutManager(UserPicturePostActivity.this, 4, GridLayoutManager.VERTICAL, false);
        ryPicture.setLayoutManager(manager);
        adapter = new GridImageAdapter(UserPicturePostActivity.this, new GridImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                ISListConfig config = new ISListConfig.Builder()
                        .multiSelect(true)
                        // 是否记住上次选中记录
                        .rememberSelected(true)
                        // 使用沉浸式状态栏
                        .maxNum(9)
                        .titleBgColor(0xffffffff)
                        .btnTextColor(0xff72D0D0)
                        .statusBarColor(Color.parseColor("#ffffff")).build();
                ISNav.getInstance().toListActivity(UserPicturePostActivity.this, config, REQUEST_LIST_CODE);
            }
        });
        adapter.setList(selectList);
        adapter.setSelectMax(9);
        ryPicture.setAdapter(adapter);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LIST_CODE && resultCode == RESULT_OK && data != null) {
            selectList.clear();
            List<String> result = data.getStringArrayListExtra("result");
            selectList.addAll(result);
            adapter.setList(result);
            adapter.notifyDataSetChanged();
        } else if (requestCode == REQUEST_CAMERA_CODE && resultCode == RESULT_OK && data != null) {
            String path = data.getStringExtra("result");
        }else if (requestCode==3&& data != null){
            Log.i(TAG, "onActivityResult: "+data.getStringExtra("topicName"));
            tvTopic.setText("# "+data.getStringExtra("topicName"));
            tvDescribe.setText("将同步到  "+data.getStringExtra("topicName")+"  圈子");
            tvDescribe.setVisibility(View.VISIBLE);
            tvTopic.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    @OnClick({R2.id.tv_topic,R2.id.iv_picture, R2.id.iv_ate, R2.id.iv_topic})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.iv_picture) {
            ISListConfig config = new ISListConfig.Builder()
                    .multiSelect(true)
                    // 是否记住上次选中记录
                    .rememberSelected(true)
                    // 使用沉浸式状态栏
                    .maxNum(9)
                    .titleBgColor(0xffffffff)
                    .btnTextColor(0xff72D0D0)
                    .statusBarColor(Color.parseColor("#ffffff")).build();
            ISNav.getInstance().toListActivity(this, config, REQUEST_LIST_CODE);
        } else if (i == R.id.iv_ate) {

        } else if (i == R.id.iv_topic) {
            startActivityForResult(new Intent(UserPicturePostActivity.this, UserPostTopicActivity.class), 3);
        }else if (i==R.id.tv_topic){
            tvDescribe.setVisibility(View.GONE);
            tvTopic.setVisibility(View.GONE);
        }
    }
}
