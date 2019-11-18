package com.wangmyng.common.fragments;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.wangmyng.common.CenterApplication;
import com.wangmyng.common.R;
import com.wangmyng.common.bean.PexelsResponse;

/**
 * @author wangmyng
 * @date 2019/11/18
 */
public class WebImageDialogFragment extends BaseDialogFragment implements View.OnClickListener {


    private static WebImageDialogFragment mInstance;
    private View mRootView;
    private PexelsResponse.PhotosBean mPhotosBean;
    private GlideWebRequestListener mGlideWebRequestListener;

    public WebImageDialogFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_base_dialog, container, false);
        return mRootView;
    }

    public static void showPhotoDetail(FragmentActivity activity, PexelsResponse.PhotosBean bean) {
        checkInstance();
        mInstance.mPhotosBean = bean;
        mInstance.show(activity.getSupportFragmentManager(), "");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadWebImage();
    }

    private void loadWebImage() {
        ImageView ivContent = mRootView.findViewById(R.id.iv_content);
        ivContent.setOnClickListener(this);
        if (mGlideWebRequestListener == null) {
            mGlideWebRequestListener = new GlideWebRequestListener();
        }
        Glide.with(CenterApplication.getContext())
                .load(mPhotosBean.getSrc().getLarge2x())
                .addListener(mGlideWebRequestListener)
                .into(ivContent);

        TextView tvAuthor = mRootView.findViewById(R.id.tv_author);
        tvAuthor.setText(mPhotosBean.getPhotographer());
        tvAuthor.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_content) {
            View authorLayout = mRootView.findViewById(R.id.layout_author);
            authorLayout.setVisibility(authorLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            return;
        }
        if(view.getId() == R.id.layout_author) {

            return;
        }
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

}
