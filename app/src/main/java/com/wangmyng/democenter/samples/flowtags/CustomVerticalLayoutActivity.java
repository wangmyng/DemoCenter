package com.wangmyng.democenter.samples.flowtags;

import android.os.Bundle;

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
@Route (path = ARouterPaths.FLOW_TAGS_ACTIVITY)
public class CustomVerticalLayoutActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_vertical_layout_activitiy);
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
}
