package com.xuyijie.tantan;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;

import com.xuyijie.module_lib.base.BaseActivity;
import com.xuyijie.module_lib.contract.EmptyContract;
import com.xuyijie.module_lib.post.UserPicturePostActivity;
import com.xuyijie.module_lib.presenter.EmptyPresenter;
import com.xuyijie.module_lib.view.CustomVideoView;
import com.xuyijie.module_login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity<EmptyContract.View, EmptyPresenter> {


    @BindView(R.id.vd_splash)
    CustomVideoView vdSplash;

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
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
//        vdSplash.setVideoPath("https://shoplogo.oss-cn-beijing.aliyuncs.com/%E6%96%B0%E5%85%B0%E5%A3%81%E7%BA%B8%E5%B7%B2%E9%80%81%E8%BE%BE078_hd.mp4");
//
//        //播放
//        vdSplash.start();
//        //循环播放
//        vdSplash.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mediaPlayer) {
//
//                mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
//                    @Override
//                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
//                        return false;
//                    }
//                });
//            }
//        });
    }

    @Override
    public void initData() {

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
        vdSplash.stopPlayback();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }
}
