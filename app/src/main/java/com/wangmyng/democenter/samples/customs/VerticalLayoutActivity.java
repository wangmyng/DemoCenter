package com.wangmyng.democenter.samples.customs;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wangmyng.common.BaseActivity;
import com.wangmyng.common.arouter.ARouterPaths;
import com.wangmyng.democenter.R;

/**
 * @author wangming37
 * @date 2019/11/20
 *
 * 自定义ViewGroup创建流式标签布局
 */
@Route (path = ARouterPaths.VERTICAL_LAYOUT_ACTIVITY)
public class VerticalLayoutActivity extends BaseActivity implements View.OnClickListener {

    private Button mBtnEnd, mBtnStart, mBtnCenter;
    private VerticalLayout mVerticalLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_layout_activitiy);
    }

    @Override
    protected void initView() {
        mVerticalLayout = findViewById(R.id.vertical_layout);
        mBtnStart = findViewById(R.id.btn_start);
        mBtnEnd = findViewById(R.id.btn_end);
        mBtnCenter = findViewById(R.id.btn_center);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mBtnStart.setOnClickListener(this);
        mBtnEnd.setOnClickListener(this);
        mBtnCenter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                mVerticalLayout.setGravity(Gravity.START);
                break;
            case R.id.btn_end:
                mVerticalLayout.setGravity(Gravity.END);
                break;
            case R.id.btn_center:
                mVerticalLayout.setGravity(Gravity.CENTER_HORIZONTAL);
                break;
        }
    }
}
