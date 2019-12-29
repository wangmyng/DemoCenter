package com.wangmyng.democenter.samples.widgets;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wangmyng.democenter.R;

/**
 * @author wangming37
 * @date 2019/12/29
 */
public class ImageDialogFragment extends DialogFragment implements View.OnClickListener {

    private TextView mTvCenter, mTvNegative, mTvPositive;
    private ImageView mImgBackground, mTvClose;
    private OnTwoButtonClickListener mOnTwoButtonClickListener;
    private OnCenterButtonClickListener mOnCenterButtonClickListener;

    public ImageDialogFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_dialog, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTvCenter = view.findViewById(R.id.tv_center);
        mTvNegative = view.findViewById(R.id.tv_negative);
        mTvPositive = view.findViewById(R.id.tv_positive);
        mTvClose = view.findViewById(R.id.img_extra_close);

    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    public ImageDialogFragment setCloseButtonImage(int imageResource) {
        mTvClose.setImageResource(imageResource);
        mTvClose.setVisibility(View.VISIBLE);
        mTvClose.setOnClickListener(this);
        return this;
    }

    public ImageDialogFragment setOnCenterButtonClickListener(OnCenterButtonClickListener listener) {
        mOnCenterButtonClickListener = listener;
        if(mTvCenter.getVisibility() ==View.VISIBLE) {
            mTvCenter.setOnClickListener(this);
        }
        return this;
    }

    public ImageDialogFragment setOnTwoButtonClickListener(OnTwoButtonClickListener listener) {
        if (mTvNegative.getVisibility() == View.VISIBLE) {
            mTvNegative.setOnClickListener(this);
        }
        if (mTvPositive.getVisibility() == View.VISIBLE) {
            mTvPositive.setOnClickListener(this);
        }
        mOnTwoButtonClickListener = listener;
        return this;
    }
    public ImageDialogFragment setButtonStyle(int style) {

        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_center:
                mOnCenterButtonClickListener.onClick();
                break;
            case R.id.tv_negative:
                mOnTwoButtonClickListener.onClickNegative();
                break;
            case R.id.tv_positive:
                mOnTwoButtonClickListener.onClickPositive();
                break;
            case R.id.img_extra_close:
                dismiss();
                break;
            default:
                break;

        }
    }


    public interface OnCenterButtonClickListener {
        void onClick();
    }

    public interface OnTwoButtonClickListener {
        void onClickPositive();

        void onClickNegative();
    }


}
