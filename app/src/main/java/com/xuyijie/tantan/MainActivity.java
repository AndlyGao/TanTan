package com.xuyijie.tantan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.xuyijie.module_circle.view.CircleFragment;
import com.xuyijie.module_home.view.HomeFragment;
import com.xuyijie.module_lib.base.BaseActivity;
import com.xuyijie.module_lib.contract.EmptyContract;
import com.xuyijie.module_lib.entity.MessageWrap;
import com.xuyijie.module_lib.post.UserPicturePostActivity;
import com.xuyijie.module_lib.presenter.EmptyPresenter;
import com.xuyijie.module_lib.view.BadgeView;
import com.xuyijie.module_lib.view.PublishDialog;
import com.xuyijie.module_lib.view.toast.ToastUtils;
import com.xuyijie.module_message.MessageFragment;
import com.xuyijie.module_user.UserFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;


public class MainActivity extends BaseActivity<EmptyContract.View, EmptyPresenter> implements View.OnClickListener, EmptyContract.View {

    @BindView(R.id.rb_home)
    RadioButton rbHome;
    @BindView(R.id.view_msg)
    View viewMsg;


    private RadioGroup bottomBar;

    private ImageView index_bottom_bar_scan;
    private FragmentManager supportFragmentManager;
    Fragment homeFragment;
    Fragment messageFragment;
    Fragment shopFragment;
    Fragment userFragment;
    private FragmentTransaction transaction;


    @Override
    public boolean isSetStatusBarTranslucent() {
        return true;
    }

    @Override
    public EmptyPresenter getPresenter() {
        return new EmptyPresenter(this);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_main;
    }

    private BadgeView badge1;

    private void remind(String count) { //BadgeView的具体使用
        // 创建一个BadgeView对象，view为你需要显示提醒的控件
        badge1.setText(count);
        badge1.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 显示的位置.右上角,BadgeView.POSITION_BOTTOM_LEFT,下左，还有其他几个属性
        badge1.setTextColor(Color.WHITE); // 文本颜色
        badge1.setBadgeBackgroundColor(Color.RED); // 提醒信息的背景颜色，自己设置
        //badge1.setBackgroundResource(R.mipmap.icon_message_png); //设置背景图片
        badge1.setTextSize(12); // 文本大小
        //badge1.setBadgeMargin(3, 3); // 水平和竖直方向的间距
        badge1.setBadgeMargin(5); //各边间隔
        //显示效果，如果已经显示，则影藏，如果影藏，则显示
        badge1.show();// 只有显示
        // badge1.hide();//影藏显示
        Log.i(TAG, "remind: " + JMessageClient.getAllUnReadMsgCount());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)      //标记和发送消息的标记一样的,包括类型和值都必须一样
    public void onRecieve(MessageWrap result) {//这个参数是传递过来的数据   类型亦必须一样
        int allUnReadMsgCount = JMessageClient.getAllUnReadMsgCount();
        Log.i(TAG, "onRecieve: " + allUnReadMsgCount);
        badge1.hide();
        if (allUnReadMsgCount == 0) {
            badge1.hide();
        } else {
            if (allUnReadMsgCount > 99) {
                remind("99+");
            } else {
                remind(allUnReadMsgCount + "");
            }
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        JMessageClient.login("1234567", "1234567", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i == 0) {
                    int allUnReadMsgCount = JMessageClient.getAllUnReadMsgCount();
                    Log.i(TAG, "onRecieve: " + allUnReadMsgCount);
                    badge1.hide();
                    if (allUnReadMsgCount == 0) {
                        badge1.hide();
                    } else {
                        if (allUnReadMsgCount > 99) {
                            remind("99+");
                        } else {
                            remind(allUnReadMsgCount + "");
                        }
                    }
                } else {
                    ToastUtils.show("消息列表出错！");
                }
            }
        });

    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        badge1 = new BadgeView(this, viewMsg);
        bottomBar = findViewById(R.id.bottomBar);
        index_bottom_bar_scan = findViewById(R.id.index_bottom_bar_scan);
        index_bottom_bar_scan.setOnClickListener(this);
        rbHome.setChecked(true);
        supportFragmentManager = getSupportFragmentManager();
        bottomBar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("RestrictedApi")
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                transaction = supportFragmentManager.beginTransaction();
                hideAllFragment(transaction);
//                Map<String, ?> all = SharePreferenceUtil.getAll();
                switch (checkedId) {
                    case R.id.rb_home:
                        if (homeFragment == null) {
                            homeFragment = new HomeFragment();
                            transaction.add(R.id.flContainer, homeFragment);
                        } else {
                            transaction.show(homeFragment);
                        }
                        break;
                    case R.id.rb_resource:
                        if (shopFragment == null) {
                            shopFragment = new CircleFragment();
                            transaction.add(R.id.flContainer, shopFragment);
                        } else {
                            transaction.show(shopFragment);
                        }
                        break;
                    case R.id.rb_chat:
//                        if (all.isEmpty()) {
//                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                            startActivityForResult(intent, 1, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
//                        } else {
                        if (messageFragment == null) {
                            messageFragment = new MessageFragment();
                            transaction.add(R.id.flContainer, messageFragment);
                        } else {
                            transaction.show(messageFragment);
                        }
//                        }
                        break;
                    case R.id.rb_user:
//                        if (all.isEmpty()) {
//                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                            startActivityForResult(intent, 1, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
//                        } else {
                        if (userFragment == null) {
                            userFragment = new UserFragment();
                            transaction.add(R.id.flContainer, userFragment);
                        } else {
                            transaction.show(userFragment);
                        }
//                        }
                        break;
                }
                transaction.commit();
            }
        });
        showFirstPosition();
    }

    private void showFirstPosition() {
        transaction = supportFragmentManager.beginTransaction();
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
            transaction.add(R.id.flContainer, homeFragment);
        } else {
            transaction.show(homeFragment);
        }
        transaction.commit();
    }

    public void hideAllFragment(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (shopFragment != null) {
            transaction.hide(shopFragment);
        }
        if (messageFragment != null) {
            transaction.hide(messageFragment);
        }
        if (userFragment != null) {
            transaction.hide(userFragment);
        }
    }

    @Override
    public void initData() {

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.index_bottom_bar_scan:
                Log.i(TAG, "onClick: ");
                if (mPublishDialog == null) {
                    mPublishDialog = new PublishDialog(MainActivity.this);
                    mPublishDialog.setCancelable(false);
                    mPublishDialog.setVedioClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(MainActivity.this, "显示视频", Toast.LENGTH_SHORT).show();
                        }
                    });

                    mPublishDialog.setDanpinClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(MainActivity.this, "显示单品", Toast.LENGTH_SHORT).show();
                        }
                    });
                    mPublishDialog.setPhotoClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(MainActivity.this, UserPicturePostActivity.class));
                            mPublishDialog.dismiss();
                        }
                    });
                }
                mPublishDialog.show();

                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityReenter: " + resultCode + data + requestCode);
        if (requestCode == 1) {
            rbHome.setChecked(true);
        }
    }

    private PublishDialog mPublishDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }


    @Override
    public void showError(String msg) {
        Log.i(TAG, "showError: " + msg);
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void hideDialog() {

    }
}
