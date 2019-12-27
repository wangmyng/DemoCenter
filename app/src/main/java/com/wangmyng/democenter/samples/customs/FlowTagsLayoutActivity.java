package com.wangmyng.democenter.samples.customs;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wangmyng.common.BaseActivity;
import com.wangmyng.common.arouter.ARouterPaths;
import com.wangmyng.common.utils.SizeUtils;
import com.wangmyng.democenter.R;

import java.util.Random;

/**
 * @author wangmyng
 * @date 2019/11/22
 */
@Route (path = ARouterPaths.FLOW_TAGS_ACTIVITY)
public class FlowTagsLayoutActivity extends BaseActivity implements View.OnClickListener {

    private Button mBtnShort, mBtnMedium, mBtnLong;
    private FlowTagsLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_tags);
    }

    @Override
    protected void initView() {
        mBtnShort = findViewById(R.id.btn_short);
        mBtnMedium = findViewById(R.id.btn_medium);
        mBtnLong = findViewById(R.id.btn_long);
        mLayout = findViewById(R.id.flow_tags_layout);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mBtnLong.setOnClickListener(this);
        mBtnMedium.setOnClickListener(this);
        mBtnShort.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_short:
                mLayout.addView(getAShort());
                break;
            case R.id.btn_medium:
                mLayout.addView(getAMedium());
                break;
            case R.id.btn_long:
                mLayout.addView(getALong());
                break;
        }
    }

    private View getAShort() {
        return getATextView(shortTexts[new Random().nextInt(shortTexts.length - 1)]);
    }

    private View getAMedium() {
        return getATextView(mediumTexts[new Random().nextInt(mediumTexts.length - 1)]);
    }

    private View getALong() {
        return getATextView(longTexts[new Random().nextInt(longTexts.length - 1)]);
    }

    private View getATextView(String text) {
        TextView tv = new TextView(this);
        tv.setBackgroundColor(getColor(R.color.white_pure));
        tv.setTextSize(14);
        tv.setPadding(padding, padding, padding, padding);
        FlowTagsLayout.MarginLayoutParams lp = new FlowTagsLayout.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = SizeUtils.dp2px(8);
        lp.rightMargin = SizeUtils.dp2px(8);
        lp.topMargin = SizeUtils.dp2px(8);
        lp.bottomMargin = SizeUtils.dp2px(8);
        tv.setLayoutParams(lp);
        tv.setText(text);
        return tv;
    }

    private int padding = SizeUtils.dp2px(12);
    private String[] shortTexts = {"tree", "river", "meet", "flower", "dance", "poem", "and", "rhythm", "..."};
    private String[] mediumTexts = {"keep moving", "feed back", "positive label", "playing boy", "sunshine beauty", ".........",};
    private String[] longTexts = {"To Be Or Not To Be", "Oh You Can Really Dance", "One Life, One Love" ,"Hymn For The Weekend", "................"};
}
