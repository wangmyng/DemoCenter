package com.wangmyng.common.fragments;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.wangmyng.common.CenterApplication;
import com.wangmyng.common.R;

/**
 * @author wangmyng
 * @date 2019/11/18
 */
public class WebImageDialogFragment extends BaseDialogFragment {


    private static WebImageDialogFragment mInstance;
    private View mRootView;
    private String mContentImageUrl;
    private GlideWebRequestListener mGlideWebRequestListener;

    public WebImageDialogFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_base_dialog, container, false);
        return mRootView;
    }

    public static void showWebImage(FragmentActivity activity, String url) {
        checkInstance();
        mInstance.setWebImageUrl(url);
        mInstance.show(activity.getSupportFragmentManager(), "");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadWebImage(mContentImageUrl);
    }

    private void loadWebImage(String url) {
        if (mGlideWebRequestListener == null) {
            mGlideWebRequestListener = new GlideWebRequestListener();
        }
        Glide.with(CenterApplication.getContext()).load(url).addListener(mGlideWebRequestListener)
                .into((ImageView) mRootView.findViewById(R.id.iv_content));
    }

    private static void checkInstance() {
        if (mInstance == null) {
            mInstance = new WebImageDialogFragment();
        }
    }

private class GlideWebRequestListener implements RequestListener<Drawable> {
    @Override
    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
        return false;
    }

    @Override
    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
        return false;
    }

}

    private void setWebImageUrl(String url) {
        mContentImageUrl = url;
    }

}
