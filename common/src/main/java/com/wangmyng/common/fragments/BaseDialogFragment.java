package com.wangmyng.common.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
 *
 */
public class BaseDialogFragment extends DialogFragment {

    private static final int CONTENT_TYPE_WEB_IMAGE = 1;
    private static final int CONTENT_TYPE_WEB_VIEW = 2;

    private int mContentType = 0;
    private String mContentImageUrl;

    public BaseDialogFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base_dialog, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            window.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black_80_p)));
        }
    }

}
