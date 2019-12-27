package com.wangmyng.democenter.samples.customs;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.wangmyng.common.utils.Utils;
import com.wangmyng.democenter.R;


public class StaticProgressRingView extends View {

    private Paint mBasicPaint = new Paint();
    private Paint mProgressPaint = new Paint();
    private int mBgRingStrokeWidth, mProgressStrokeWidth;
    private float mProgress;
    private int mStartAngle = -90;
    private int mProgressStartColor;
    private int mProgressEndColor;

    public StaticProgressRingView(Context context) {
        super(context);
    }

    public StaticProgressRingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public StaticProgressRingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.StaticProgressRingView);
        final float scale = context.getResources().getDisplayMetrics().density;
        int bgStrokeWidthDP = ta.getInt(R.styleable.StaticProgressRingView_background_stroke_width_dp,2);
        mBgRingStrokeWidth = (int) (bgStrokeWidthDP * scale + 0.5);
        int prStrokeWidthDP = ta.getInt(R.styleable.StaticProgressRingView_progress_stroke_width_dp,4);
        mProgressStrokeWidth = (int) (prStrokeWidthDP * scale + 0.5);
        mProgress = ta.getFloat(R.styleable.StaticProgressRingView_progress_percent, 100)/100;
        mProgressStartColor = ta.getColor(R.styleable.StaticProgressRingView_start_color, Color.parseColor("#86E66E"));
        mProgressEndColor = ta.getColor(R.styleable.StaticProgressRingView_end_color, Color.parseColor("#00BA69"));
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*画背景圆环*/
        getBackgroundRingPaint();
        float centerPointX = getMeasuredWidth() / 2;
        float centerPointY = getMeasuredHeight() / 2;
        float radius = getMeasuredWidth() / 2 - mBgRingStrokeWidth;
        canvas.drawCircle(centerPointX, centerPointY, radius, mBasicPaint);
        /*画进度圆环*/
        getProgressRingPaint(centerPointX, centerPointY);
        canvas.drawArc(
                mBgRingStrokeWidth, mBgRingStrokeWidth,
                getMeasuredWidth() - mBgRingStrokeWidth, getMeasuredHeight() - mBgRingStrokeWidth,
                mStartAngle, 360 * mProgress, false, mProgressPaint);
        /*处理圆头带来的的额外距离*/
        mBasicPaint.setStrokeWidth(mProgressStrokeWidth);
        if(mProgress == 1){
            mBasicPaint.setColor(mProgressEndColor);
            canvas.drawPoint(centerPointX, mBgRingStrokeWidth, mBasicPaint);
        }else {
            mBasicPaint.setColor(mProgressStartColor);
            canvas.drawPoint(centerPointX, mBgRingStrokeWidth, mBasicPaint);
        }
    }

    /**
     * 获取背景圆环的画笔
     */
    private void getBackgroundRingPaint() {
        mBasicPaint.setStrokeCap(Paint.Cap.ROUND);
        mBasicPaint.setStyle(Paint.Style.STROKE);
        mBasicPaint.setAntiAlias(true);
        mBasicPaint.setColor(Color.parseColor("#BFBFBF"));
        mBasicPaint.setStrokeWidth(mBgRingStrokeWidth);
    }

    /**
     * 获取进度圆环的画笔
     *
     * @param centerPointX 圆点x
     * @param centerPointY 圆点y
     */
    private void getProgressRingPaint(float centerPointX, float centerPointY) {
        mProgressPaint.setStrokeCap(Paint.Cap.ROUND);
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setStrokeWidth(mProgressStrokeWidth);
        int[] colors = new int[]{mProgressStartColor, mProgressEndColor};
        float[] positions = new float[]{0, 1};
        Shader shader = new SweepGradient(centerPointX, centerPointY, colors, positions);
        Matrix matrix = new Matrix();
        matrix.setRotate(-90, centerPointX, centerPointY);
        shader.setLocalMatrix(matrix);
        mProgressPaint.setShader(shader);
    }

    public static int dp2px(float dpValue) {
        final float scale = Utils.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
