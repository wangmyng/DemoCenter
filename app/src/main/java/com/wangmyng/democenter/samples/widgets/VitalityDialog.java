package com.wangmyng.democenter.samples.widgets;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.wangmyng.democenter.R;

/**
 * @author wangmyng
 * date 2020/1/1
 *
 * 整合几个不同布局的dialog
 *
 *
 * demo：
 *
 *     * @param content  弹窗内容
 *     * @param listener 居中按钮的监听，没按钮传空
 *     public static void showOpenRedDialog(Context context, String content, @Nullable View.OnClickListener listener) {
 *         new VitalityDialog.Builder().setContext(context).setContent(content)
 *                 .setDialogType(listener == null ? VitalityDialog.TYPE_OPEN_RED_0_BUTTON : VitalityDialog.TYPE_OPEN_RED_1_BUTTON)
 *                 .setOnCenterButtonClickListener(listener).build().show();
 *     }
 *
 *
 *    * @param content            弹窗内容
 *    * @param onClickListener    居中按钮的监听，没按钮传空
 *    * @param onSelectedListener 选择按钮的监听，没按钮传空
 *
 *    public static void showGetPrizeDialog(Context context, String content, @Nullable View.OnClickListener onClickListener, @Nullable VitalityDialog.OnSelectedListener onSelectedListener) {
 *         int type = VitalityDialog.TYPE_GET_PRIZE_0_BUTTON;
 *         if (onClickListener != null) type = VitalityDialog.TYPE_GET_PRIZE_1_BUTTON;
 *         if (onSelectedListener != null) type = VitalityDialog.TYPE_GET_PRIZE_2_BUTTON;
 *         new VitalityDialog.Builder().setContext(context).setDialogType(type).setContent(content)
 *                 .setOnCenterButtonClickListener(onClickListener)
 *                 .setOnSelectedListener(onSelectedListener)
 *                 .build().show();
 *     }
 *
 *
 *     * @param title1   第一个标题
 *     * @param title2   第二个标题
 *     * @param content1 第一部分内容
 *     * @param content2 第二部分内容
 *     * @param listener 居中按钮的监听
 *
 *     public static void showRulesDialog(Context context, String title1, String title2, String content1, String content2, View.OnClickListener listener) {
 *         new VitalityDialog.Builder().setContext(context)
 *                 .setDialogType(VitalityDialog.TYPE_RULES)
 *                 .setTitle(title1).setTitle2(title2).setContent(content1).setContent2(content2)
 *                 .setOnCenterButtonClickListener(listener)
 *                 .build().show();
 *     }
 *
 */
public class VitalityDialog extends Dialog implements View.OnClickListener {

    private TextView tvTitle1;
    private TextView tvTitle2;
    private TextView tvContent1;
    private TextView tvContent2;
    private ImageView btnClose;
    private ImageView btnCenter;
    private ImageView btnCancel;
    private ImageView btnConfirm;

    private Builder mBuilder;

    //计分规则弹窗
    public static final int TYPE_RULES = 0;
    //开门红弹窗，无按钮
    public static final int TYPE_OPEN_RED_0_BUTTON = 1;
    //开门红弹窗，有按钮
    public static final int TYPE_OPEN_RED_1_BUTTON = 2;
    //领取奖品弹窗，无按钮
    public static final int TYPE_GET_PRIZE_0_BUTTON = 3;
    //领取奖品弹窗，1个按钮
    public static final int TYPE_GET_PRIZE_1_BUTTON = 4;
    //领取奖品弹窗，2个按钮
    public static final int TYPE_GET_PRIZE_2_BUTTON = 5;

    private VitalityDialog(Builder builder) {
        super(builder.context, R.style.dialog_custom);
        mBuilder = builder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("myng", "dialog creating.........");
        super.onCreate(savedInstanceState);

        initView();
        initListener();
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 宽度全屏
        WindowManager windowManager = ((Activity) mBuilder.context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = window.getAttributes();
        Point p = new Point();
        display.getSize(p);
        lp.width = p.x * 9 / 10; // 设置dialog宽度为屏幕的9/10
        lp.height = p.y * 8 / 10;
        lp.gravity = Gravity.CENTER;
        getWindow().setAttributes(lp);
    }

    private void initView() {
        switch (mBuilder.dialogType) {
            case TYPE_RULES:
                setContentView(R.layout.dialog_rules);
                tvTitle1 = findViewById(R.id.tv_title_1);
                tvTitle2 = findViewById(R.id.tv_title_2);
                tvContent1 = findViewById(R.id.tv_content_1);
                tvContent2 = findViewById(R.id.tv_content_2);
                btnCenter = findViewById(R.id.btn_center);
                break;
            case TYPE_OPEN_RED_0_BUTTON:
                setContentView(R.layout.dialog_open_red_no_btn);
                tvContent1 = findViewById(R.id.tv_content);
                break;
            case TYPE_OPEN_RED_1_BUTTON:
                setContentView(R.layout.dialog_open_red_one_btn);
                tvContent1 = findViewById(R.id.tv_content);
                btnCenter = findViewById(R.id.btn_center);
                break;
            case TYPE_GET_PRIZE_0_BUTTON:
                setContentView(R.layout.dialog_get_prize_no_btn);
                tvContent1 = findViewById(R.id.tv_content);
                break;
            case TYPE_GET_PRIZE_1_BUTTON:
                setContentView(R.layout.dialog_get_prize_one_btn);
                tvContent1 = findViewById(R.id.tv_content);
                btnCenter = findViewById(R.id.btn_center);
                break;
            case TYPE_GET_PRIZE_2_BUTTON:
                setContentView(R.layout.dialog_get_prize_two_btn);
                tvContent1 = findViewById(R.id.tv_content);
                btnCancel = findViewById(R.id.btn_cancel);
                btnConfirm = findViewById(R.id.btn_confirm);
                break;
            default:
                break;
        }
        btnClose = findViewById(R.id.btn_close);
        if (tvTitle1 != null) tvTitle1.setText(mBuilder.title1);
        if (tvTitle2 != null) tvTitle2.setText(mBuilder.title2);
        if (tvContent1 != null) tvContent1.setText(mBuilder.content1);
        if (tvContent2 != null) tvContent2.setText(mBuilder.content2);
    }

    private void initListener() {
        if (btnCenter != null)
            btnCenter.setOnClickListener(this);
        if (btnCancel != null)
            btnCancel.setOnClickListener(this);
        if (btnConfirm != null)
            btnConfirm.setOnClickListener(this);
        if (btnClose != null)
            btnClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close:
                break;
            case R.id.btn_center:
                mBuilder.onClickListener.onClick(v);
                break;
            case R.id.btn_cancel:
                mBuilder.onSelectedListener.onNegativeSelected();
                break;
            case R.id.btn_confirm:
                mBuilder.onSelectedListener.onPositiveSelected();
                break;
            default:
                break;
        }
        dismiss();
    }

    public interface OnSelectedListener {
        void onNegativeSelected();

        void onPositiveSelected();
    }

    public static class Builder {
        private Context context;
        private String title1, title2, content1, content2;
        private int dialogType;
        private View.OnClickListener onClickListener;
        private OnSelectedListener onSelectedListener;

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setTitle(String tittle) {
            title1 = tittle;
            return this;
        }

        public Builder setTitle2(String tittle) {
            title2 = tittle;
            return this;
        }

        public Builder setContent(String content) {
            content1 = content;
            return this;
        }

        public Builder setContent2(String content) {
            content2 = content;
            return this;
        }

        public Builder setDialogType(int type) {
            this.dialogType = type;
            return this;
        }

        public Builder setOnCenterButtonClickListener(View.OnClickListener listener) {
            onClickListener = listener;
            return this;
        }

        public Builder setOnSelectedListener(OnSelectedListener listener) {
            onSelectedListener = listener;
            return this;
        }

        public VitalityDialog build() {
            return new VitalityDialog(this);
        }
    }
}
