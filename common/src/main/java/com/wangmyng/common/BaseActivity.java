package com.wangmyng.common;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public abstract class BaseActivity extends FragmentActivity {

    public final String TAG = this.getClass().getSimpleName();

    protected Activity mActivity = this;
    protected Context mStaticContext = CenterApplication.getContext();

    private boolean hasInitView = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initListener();

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("currentClass", "onStart..." + TAG);

        // TODO: 2019/7/13 直接放在 onCreate中不行，子类中会空指针
        if(!hasInitView) {
            initView();
            initListener();
            initData();
            hasInitView = true;
        }
    }

}
