package com.xuyijie.module_lib.base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.xuyijie.module_lib.R;
import com.xuyijie.module_lib.contract.EmptyContract;
import com.xuyijie.module_lib.presenter.EmptyPresenter;

public class VoicePostActivity extends BaseActivity<EmptyContract.View, EmptyPresenter> {



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
        return R.layout.activity_voice_post;
    }

    @Override
    public void initView() {
        EventManager asr = EventManagerFactory.create(this, "asr");
    }

    @Override
    public void initData() {

    }
}
