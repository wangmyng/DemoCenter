package com.wangmyng.democenter.samples.timer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wangmyng.common.BaseActivity;
import com.wangmyng.common.arouter.ARouterPaths;
import com.wangmyng.democenter.R;

/**
 * @author wangming37
 * @date 2019/11/15
 *
 * 一个简单地计时器，通过Handler延时1秒的消息循环完成数字递增
 */
@Route (path = ARouterPaths.TIMER_ACTIVITY)
public class TimerActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvTimer;
    private Button mBtnStart, mBtnStop;
    private boolean isRunning;
    private int mTime = 0;
    private Handler mHandler;
    private TimeCounterRunnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        if (savedInstanceState != null) {
            isRunning = savedInstanceState.getBoolean("isRunning");
            mTime = savedInstanceState.getInt("time");
        }
    }

    @Override
    protected void initView() {
        mTvTimer = findViewById(R.id.tv_time);
        mBtnStart = findViewById(R.id.btn_start);
        mBtnStop = findViewById(R.id.btn_stop);
    }

    @Override
    protected void initData() {
        setTime();
        mHandler = new Handler();
        mRunnable = new TimeCounterRunnable();
        if (isRunning) {
            mHandler.postDelayed(mRunnable, 1000);
        }
        mBtnStart.setText(isRunning ? "Pause" : "Start");
    }

    private class TimeCounterRunnable implements Runnable {

        @Override
        public void run() {
            mTime++;
            setTime();
            mHandler.postDelayed(this, 1000);
        }
    }

    @Override
    protected void initListener() {
        mBtnStart.setOnClickListener(this);
        mBtnStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                toggleTimer();
                break;
            case R.id.btn_stop:
                resetTimer();
                break;
            default:
                break;
        }
    }

    private void toggleTimer() {
        isRunning = !isRunning;
        mBtnStart.setText(isRunning ? "Pause" : "Start");
        if (isRunning) {
            mHandler.postDelayed(mRunnable, 1000);
        } else {
            mHandler.removeCallbacks(mRunnable);
        }
    }

    private void resetTimer() {
        mHandler.removeCallbacks(mRunnable);
        isRunning = false;
        mTime = 0;
        setTime();
    }

    @SuppressLint ("DefaultLocale")
    private void setTime() {
        int hours = mTime / 3600;
        int minutes = mTime % 3600 / 60;
        int seconds = mTime % 60;
        mTvTimer.setText(String.format("%02d : %02d : %02d", hours, minutes, seconds));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isRunning", isRunning);
        outState.putInt("time", mTime);
    }
}
