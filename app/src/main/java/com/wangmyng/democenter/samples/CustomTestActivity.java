package com.wangmyng.democenter.samples;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wangmyng.common.BaseActivity;
import com.wangmyng.common.arouter.ARouterPaths;
import com.wangmyng.democenter.R;
import com.wangmyng.democenter.samples.widgets.ImageDialogFragment;

/**
 * @author wangming37
 * @date 2019/12/29
 *
 */
@Route(path = ARouterPaths.CUSTOM_TEST_ACTIVITY)
public class CustomTestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_test);
    }

    @Override
    protected void initView() {
        ImageDialogFragment dialogFragment = new ImageDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
}
