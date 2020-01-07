package com.wangmyng.democenter.samples;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wangmyng.common.BaseActivity;
import com.wangmyng.common.arouter.ARouterPaths;
import com.wangmyng.common.utils.SizeUtils;
import com.wangmyng.common.utils.ToastUtils;
import com.wangmyng.democenter.R;
import com.wangmyng.democenter.samples.widgets.TopRightListPopupWindow;

/**
 * @author wangming37
 * @date 2019/12/29
 */
@Route (path = ARouterPaths.CUSTOM_TEST_ACTIVITY)
public class CustomTestActivity extends BaseActivity implements View.OnClickListener {


    final String[] items = new String[]{"2020年春季1～3月", "2020年春季1～3月", "2020年春季1～3月", "2020年冬季10～12月"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_test);
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        findViewById(R.id.right_icon).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.right_icon) {
            showPopUpWindow();
        }
    }

    TopRightListPopupWindow mPopupWindow;

    private void showPopUpWindow() {
        if (mPopupWindow == null) {
            mPopupWindow = new TopRightListPopupWindow(this);
            mPopupWindow.setBackgroundAlpha(0.7f)
                    .setDataList(items)
                    .setWindowWidth(SizeUtils.dp2px(180))
                    .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            ToastUtils.showShortToast(items[position]);
                        }
                    });
        }
        mPopupWindow.showAsDropDown(findViewById(R.id.right_icon), -SizeUtils.dp2px(18), SizeUtils.dp2px(5), Gravity.END);
    }
}
