package com.wangmyng.democenter.samples.flowtags;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

class VerticalLayout extends ViewGroup {

    public VerticalLayout(Context context) {
        super(context);
    }

    public VerticalLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VerticalLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int mChildrenLayoutGravity = 0;

    public void setChildrenLayoutGravity(int gravity) {

    }

    private int mGravity = 0;

    public void setGravity(int gravity) {
        if (gravity == mGravity) return;
        mGravity = gravity;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMeasureSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMeasureSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMeasureSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMeasureSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        int measuredWidth = 0;
        int measuredHeight = 0;

        //当此ViewGroup布局属性设置为matchParent或具体数值时，不需要在遍历测量子View时进行计算获取了
        LayoutParams lp = getLayoutParams();
        switch (widthMeasureSpecMode) {
            case MeasureSpec.EXACTLY:
            case MeasureSpec.AT_MOST:
                if (lp.width > 0) {
                    measuredWidth = lp.width;
                } else if (lp.width == LayoutParams.MATCH_PARENT) {
                    measuredWidth = widthMeasureSpecSize;
                }
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        switch (heightMeasureSpecMode) {
            case MeasureSpec.EXACTLY:
            case MeasureSpec.AT_MOST:
                if (lp.height > 0) {
                    measuredHeight = lp.height;
                } else if (lp.height == LayoutParams.MATCH_PARENT) {
                    measuredHeight = heightMeasureSpecSize;
                }
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        boolean skipMeasureWidth = measuredWidth != 0;
        boolean skipMeasureHeight = measuredHeight != 0;

        //遍历子View，测量并记录宽高
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            MarginLayoutParams p = (MarginLayoutParams) childView.getLayoutParams();
            int totalWidth = childView.getMeasuredWidth() + p.leftMargin + p.rightMargin;
            int totalHeight = childView.getMeasuredHeight() + p.topMargin + p.bottomMargin;
            measureChildWithMargins(
                    childView, widthMeasureSpec, totalWidth, heightMeasureSpec, totalHeight);
            //先用简单纵向布局进行测试
            if (!skipMeasureWidth)
                measuredWidth = Math.max(measuredWidth, totalWidth);
            if (!skipMeasureHeight)
                measuredHeight += totalHeight;
        }

        //完成设置布局的宽高值
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int defaultL = l + getPaddingLeft();
        int defaultT = t + getPaddingTop();
        int defaultR = r - getPaddingRight();
        int defaultB = b - getPaddingBottom();
        int curL = defaultL, curR = defaultR, curT = defaultT, curB = defaultB;
        MarginLayoutParams lp;
        switch (mGravity) {
            case Gravity.END:
                for (int i = 0; i < getChildCount(); i++) {
                    View child = getChildAt(i);
                    lp = (MarginLayoutParams) child.getLayoutParams();
                    curR = defaultR - lp.rightMargin;
                    curL = curR - child.getMeasuredWidth();
                    curT += lp.topMargin;
                    curB = curT + child.getMeasuredHeight();
                    child.layout(curL, curT, curR, curB);
                    curT += child.getMeasuredHeight() + lp.bottomMargin;
                }
                break;
            case Gravity.CENTER_HORIZONTAL:
                int hCenterX = (l + r) / 2;
                for (int i = 0; i < getChildCount(); i++) {
                    View child = getChildAt(i);
                    lp = (MarginLayoutParams) child.getLayoutParams();
                    curL = hCenterX - child.getMeasuredWidth()/2;
                    curT += lp.topMargin;
                    curR = hCenterX + child.getMeasuredWidth()/2;
                    curB = curT + child.getMeasuredHeight();
                    child.layout(curL, curT, curR, curB);
                    curT += child.getMeasuredHeight() + lp.bottomMargin;
                }
                break;
            default:
                for (int i = 0; i < getChildCount(); i++) {
                    View child = getChildAt(i);
                    lp = (MarginLayoutParams) child.getLayoutParams();
                    curL += lp.leftMargin;
                    curR = curL + child.getMeasuredWidth();
                    curT += lp.topMargin;
                    curB = curT + child.getMeasuredHeight();
                    child.layout(curL, curT, curR, curB);
                    curL = l + getPaddingStart();
                    curT += child.getMeasuredHeight() + lp.bottomMargin;
                }
                break;
        }
    }


    /*要想使布局中的子View设置的margin属性生效，需要重写以下方法*/

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MyLayoutParams(getContext(), attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        return new MyLayoutParams(lp);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MyLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    public static class MyLayoutParams extends MarginLayoutParams {

        MyLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        MyLayoutParams(int width, int height) {
            super(width, height);
        }

        MyLayoutParams(LayoutParams lp) {
            super(lp);
        }
    }
}
