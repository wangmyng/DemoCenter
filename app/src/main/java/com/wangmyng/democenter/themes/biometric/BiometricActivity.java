package com.wangmyng.democenter.themes.biometric;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wangmyng.common.BaseActivity;
import com.wangmyng.democenter.R;
import com.wangmyng.common.arouter.ARouterPaths;


@Route(path = ARouterPaths.BIOMETRIC_ACTIVITY)
public class BiometricActivity extends BaseActivity {

    private TextView tvHello;
    private BiometricClientHelper mClientHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biometric);

    }


    @Override
    protected void initView() {
        tvHello = findViewById(R.id.tv_hello);
    }

    @Override
    protected void initData() {
        initFingerPrintPrompt();
    }

    @Override
    protected void initListener() {
        tvHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClientHelper.authenticateFingerprint();
            }
        });
    }

    private void initFingerPrintPrompt() {

        mClientHelper = new BiometricClientHelper(mActivity);
        mClientHelper.getFingerprintFragmentBuilder()
                .setTitle("title")
                .setSubtitle("subtitle")
                .setDescription("description")
                .setNegativeText("negative")
                .setAuthenticationCallBack(new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                        Log.d("mmmm", "onAuthenticationError: " + errString);
                    }

                    @Override
                    public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        Log.d("mmmm", "onAuthenticationSucceeded: ");
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                        Log.d("mmmm", "onAuthenticationFailed: ");
                    }
                }).build();
    }
}
