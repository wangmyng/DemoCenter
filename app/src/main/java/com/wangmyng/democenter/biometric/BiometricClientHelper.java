package com.wangmyng.democenter.biometric;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import java.util.concurrent.Executor;


/**
 * Author: wangming37
 * Date: 2019/1/21
 *
 * 创建
 * BiometricClientHelper helper = new BiometricClientHelper(getActivity());
 * 配置
 * helper.getFingerprintFragmentBuilder()
 *      .setTitle(...)  //标题
 *      .setNegativeText(...)   // cancel键文本
 *      .setAuthenticationCallBack(new AuthenticationCallBack(){...})   //验证结果回调
 *      .setOnFingerprintInvalidatedListener(new OnFingerprintInvalidatedListener(){...}) {   //新录入指纹监听
 *      .build();
 * 开始指纹验证
 * helper.authenticateFingerprint();
 *
 * 信任新指纹
 * helper.validateFingerprint();
 *
 */
public class BiometricClientHelper {

    private FragmentActivity mActivity;
    private FingerprintFragmentBuilder builder;
    private BiometricPrompt mFingerprintPrompt;
    private CryptoObjectHelper mCryptoObjectHelper;

    public BiometricClientHelper(Activity activity) {
        mActivity = (FragmentActivity) activity;
    }

    public FingerprintFragmentBuilder getFingerprintFragmentBuilder() {
        return builder = new FingerprintFragmentBuilder();
    }

    public static class FingerprintFragmentBuilder {
        Bundle bundle = new Bundle();
        BiometricPrompt.AuthenticationCallback callback;
        BiometricPrompt.PromptInfo fingerprintPromptInfo;
        Executor executor;
        OnFingerprintInvalidatedListener fingerprintInvalidatedListener;

        /**
         * @param title 指纹验证弹窗的标题
         */
        @NonNull
        public FingerprintFragmentBuilder setTitle(@NonNull CharSequence title) {
            bundle.putCharSequence(BiometricPrompt.KEY_TITLE, title);
            return this;
        }

        /**
         * @param subtitle 指纹验证弹窗的副标题
         */
        public FingerprintFragmentBuilder setSubtitle(CharSequence subtitle) {
            bundle.putCharSequence(BiometricPrompt.KEY_SUBTITLE, subtitle);
            return this;
        }

        /**
         * @param description 指纹验证弹窗的补充描述信息
         */
        public FingerprintFragmentBuilder setDescription(CharSequence description) {
            bundle.putCharSequence(BiometricPrompt.KEY_DESCRIPTION, description);
            return this;
        }

        /**
         * @param negativeText 指纹验证弹窗的 cancel 键文本
         */
        @NonNull
        public FingerprintFragmentBuilder setNegativeText(@NonNull CharSequence negativeText) {
            bundle.putCharSequence(BiometricPrompt.KEY_NEGATIVE_TEXT, negativeText);
            return this;
        }

        /**
         * @param callBack 指纹验证结果的回调接口
         * errorCode 返回值详见 {@link BiometricConstants}
         */
        @NonNull
        public FingerprintFragmentBuilder setAuthenticationCallBack(
                @NonNull BiometricPrompt.AuthenticationCallback callBack) {
            callback = callBack;
            return this;
        }

        /**
         * 新指纹录入的监听
         * 当有新的指纹录入时，指纹验证有安全风险，可以在此完成 “使用密码登录” 等其他验证方式的逻辑
         * <p>
         * 此监听一旦设置，当有新指纹录入后指纹验证会一直失效，直到手动调用{@link #validateFingerprint()}恢复
         */
        public FingerprintFragmentBuilder setOnFingerprintInvalidatedListener(
                OnFingerprintInvalidatedListener listener) {
            fingerprintInvalidatedListener = listener;
            return this;
        }

        /**
         * 完成指纹识别弹窗界面的初始化
         */
        public void build() {
            final CharSequence title = bundle.getCharSequence(BiometricPrompt.KEY_TITLE);
            final CharSequence negative = bundle.getCharSequence(BiometricPrompt.KEY_NEGATIVE_TEXT);
            if (TextUtils.isEmpty(title)) {
                throw new IllegalArgumentException("Title must be set and non-empty");
            } else if (TextUtils.isEmpty(negative)) {
                throw new IllegalArgumentException("Negative button text must be set and "
                        + "non-empty");
            }
            executor = new Executor() {
                @Override
                public void execute(@NonNull Runnable command) {
                    new Handler(Looper.getMainLooper()).post(command);
                }
            };
            fingerprintPromptInfo = new BiometricPrompt.PromptInfo.Builder()
                    .setTitle(bundle.getCharSequence(BiometricPrompt.KEY_TITLE, ""))
                    .setSubtitle(bundle.getCharSequence(BiometricPrompt.KEY_SUBTITLE, ""))
                    .setDescription(bundle.getCharSequence(BiometricPrompt.KEY_DESCRIPTION, ""))
                    .setNegativeButtonText(bundle.getCharSequence(BiometricPrompt.KEY_NEGATIVE_TEXT, ""))
                    .build();
        }
    }

    /**
     * 开启指纹验证服务
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void authenticateFingerprint() {
        BiometricPrompt.CryptoObject cryptoObject;
        try {
            if (mCryptoObjectHelper == null) {
                mCryptoObjectHelper = new CryptoObjectHelper();
            }
            cryptoObject = mCryptoObjectHelper.getCryptoObjectForBiometricPrompt();
            if (cryptoObject == null) {
                return;
            }
            if (mFingerprintPrompt == null) {
                mFingerprintPrompt = new BiometricPrompt(mActivity, builder.executor, builder.callback);
            }
            mFingerprintPrompt.authenticate(builder.fingerprintPromptInfo, cryptoObject);
        } catch (KeyPermanentlyInvalidatedException e) {
            if (builder.fingerprintInvalidatedListener == null) {
                validateFingerprint();
                authenticateFingerprint();
            } else {
                builder.fingerprintInvalidatedListener.onFingerprintInvalidated();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OnFingerprintInvalidatedListener {
        void onFingerprintInvalidated();
    }


    /**
     * 重置指纹验证的有效性
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void validateFingerprint() {
        try {
            if (mCryptoObjectHelper == null) {
                mCryptoObjectHelper = new CryptoObjectHelper();
            }
            mCryptoObjectHelper.resetKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
