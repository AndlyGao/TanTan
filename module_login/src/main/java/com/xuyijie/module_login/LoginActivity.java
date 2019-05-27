package com.xuyijie.module_login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.xuyijie.module_lib.base.BaseActivity;
import com.xuyijie.module_lib.contract.EmptyContract;
import com.xuyijie.module_lib.presenter.EmptyPresenter;
import com.xuyijie.module_lib.view.CountDownTimerUtils;
import com.xuyijie.module_lib.view.toast.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class LoginActivity extends BaseActivity<EmptyContract.View, EmptyPresenter> {

    @BindView(R2.id.iv_close)
    ImageView ivClose;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_menu)
    TextView tvMenu;
    @BindView(R2.id.tb_common)
    RelativeLayout tbCommon;
    @BindView(R2.id.fl_title)
    FrameLayout flTitle;
    @BindView(R2.id.tv_welcome)
    TextView tvWelcome;
    @BindView(R2.id.et_tel)
    EditText etTel;
    @BindView(R2.id.et_code)
    EditText etCode;
    @BindView(R2.id.tv_send_sms)
    TextView tvSendSms;
    @BindView(R2.id.rl_code)
    RelativeLayout rlCode;
    @BindView(R2.id.tv_login)
    TextView tvLogin;
    @BindView(R2.id.iv_qq_login)
    TextView ivQqLogin;
    private CountDownTimerUtils countDownTimer;
    private Tencent mTencent;

    @Override
    public boolean isSetStatusBarTranslucent() {
        return true;
    }

    @Override
    public EmptyPresenter getPresenter() {
        return null;
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_login;
    }


    @Override
    public void initView() {
        ButterKnife.bind(this);
        initToolBar().setToolBarTitle("");
        countDownTimer = new CountDownTimerUtils(tvSendSms, 60000, 1000);


    }

    //返回重启加载
    @Override
    protected void onRestart() {
        super.onRestart();
        initView();
    }

    //防止锁屏或者切出的时候，音乐在播放
    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    @OnClick({R2.id.tv_send_sms, R2.id.tv_login, R2.id.iv_qq_login})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.tv_send_sms) {
            countDownTimer.start();
            BmobSMS.requestSMSCode(etTel.getText().toString(), "DataSDK", new QueryListener<Integer>() {
                @Override
                public void done(Integer smsId, BmobException e) {
                    if (e == null) {
                        ToastUtils.show("发送验证码成功");

                    } else {
                        ToastUtils.show("发送验证码失败：" + e.getMessage());
                    }
                }
            });

        } else if (i == R.id.tv_login) {

        } else if (i == R.id.iv_qq_login) {
            mTencent = Tencent.createInstance("1109041063", this);
            mTencent.login(LoginActivity.this, "all", iUiListener);


        }
    }

    IUiListener iUiListener = new IUiListener() {
        @Override
        public void onComplete(Object o) {
            initOpenIdAndToken(o);
            Log.i(TAG, "onComplete: " + o.toString());
            getUserInfo();
//            UserInfo userInfo = new UserInfo(LoginActivity.this, mTencent.getQQToken());
//            Log.i(TAG, "onComplete: "+userInfo.toString());

        }

        @Override
        public void onError(UiError uiError) {
            ToastUtils.show(uiError.errorMessage);
        }

        @Override
        public void onCancel() {
            ToastUtils.show("登陆取消");
        }
    };

    private void initOpenIdAndToken(Object object) {
        JSONObject jb = (JSONObject) object;
        try {
            String openID = jb.getString("openid");  //openid用户唯一标识
            String access_token = jb.getString("access_token");
            String expires = jb.getString("expires_in");

            mTencent.setOpenId(openID);
            mTencent.setAccessToken(access_token, expires);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getUserInfo() {
        QQToken token = mTencent.getQQToken();
        UserInfo mInfo = new UserInfo(LoginActivity.this, token);
        mInfo.getUserInfo(new IUiListener() {
            @Override
            public void onComplete(Object object) {
                JSONObject jb = (JSONObject) object;
                try {
//                    name = jb.getString("nickname");
                    String figureurl = jb.getString("figureurl_qq_2");  //头像图片的url
                    Log.i(TAG, "onComplete: " + figureurl);
//                    /*Log.i("imgUrl",figureurl.toString()+"");*/
//                    nickName.setText(name);
//                    /*Glide.with(MainActivity.this).load(figureurl).into(figure);*/
//                    Uri parse = Uri.parse(figureurl);
//                    figure.setImageURI(parse);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(UiError uiError) {
            }

            @Override
            public void onCancel() {
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        mTencent.onActivityResultData(requestCode, resultCode, data, iUiListener);
        if (requestCode == Constants.REQUEST_LOGIN) {
//            mTencent.handleResultData(data, iUiListener);
            if (resultCode == -1) {
                Tencent.onActivityResultData(requestCode, resultCode, data, iUiListener);

                Tencent.handleResultData(data, iUiListener);

                UserInfo info = new UserInfo(this, mTencent.getQQToken());
                info.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object o) {


                        JSONObject info = (JSONObject) o;
//                            String nickName = info.getString("nickname");//获取用户昵称
//                            String iconUrl = info.getString("figureurl_qq_2");//获取用户头像的url

                        Log.i("TAG", "Username" + info);
//                            tvUsername.setText(nickName);
//                            Glide.with(MainActivity.this).load(iconUrl).into(icon_image);//Glide解析获取用户头像

                    }

                    @Override
                    public void onError(UiError uiError) {

                        Log.e("GET_QQ_INFO_ERROR", "获取qq用户信息错误");
                        Toast.makeText(LoginActivity.this, "获取qq用户信息错误", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {

                        Log.e("GET_QQ_INFO_CANCEL", "获取qq用户信息取消");
                        Toast.makeText(LoginActivity.this, "获取qq用户信息取消", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}
