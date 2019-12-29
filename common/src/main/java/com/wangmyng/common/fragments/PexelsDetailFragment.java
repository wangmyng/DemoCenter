package com.wangmyng.common.fragments;


import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.wangmyng.common.R;

/**
 * @author wangmyng
 * @date 2019/11/18
 *
 */
public class PexelsDetailFragment extends DialogFragment {

    private static final int CONTENT_TYPE_WEB_IMAGE = 1;
    private static final int CONTENT_TYPE_WEB_VIEW = 2;

    private int mContentType = 0;
    private String mContentImageUrl;

    public PexelsDetailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pexels_detail, container, false);
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
