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
            if (!skipMeasureWidth)
                measuredWidth = Math.max(measuredWidth, totalWidth);
            if (!skipMeasureHeight)
                measuredHeight += totalHeight;
        }

        measuredWidth += getPaddingStart() + getPaddingEnd();
        measuredHeight += getPaddingTop() + getPaddingBottom();

        //完成设置布局的宽高值
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    /**
     * 注意：参数中的l,t,r,b表示此控件与它的父控件的相对距离，而不是单纯的坐标
     * 同样的，在此控件调用child的layout方法时，传入的参数也需要是与此控件的相对距离
     * 相对距离是指以父控件的左上顶点为原点，自身的左上右下到父控件的左边界（y轴）和上边界（x轴）的距离
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int curL, curT, curR, curB;
        curT = getPaddingTop();
        MarginLayoutParams lp;
        View child;
        for (int i = 0; i < getChildCount(); i++) {
            child = getChildAt(i);
            lp = (MarginLayoutParams) child.getLayoutParams();
            switch (mGravity) {
                case Gravity.END:
                    curR = r - l - getPaddingEnd() - lp.rightMargin;
                    curL = curR - child.getMeasuredWidth();
                    curT += lp.topMargin;
                    curB = curT + child.getMeasuredHeight();
                    child.layout(curL, curT, curR, curB);
                    curT += child.getMeasuredHeight() + lp.bottomMargin;
                    break;
                case Gravity.CENTER_HORIZONTAL:
                    int hCenterX = (l + r) / 2;
                    curL = hCenterX - child.getMeasuredWidth() / 2;
                    curT += lp.topMargin;
                    curR = hCenterX + child.getMeasuredWidth() / 2;
                    curB = curT + child.getMeasuredHeight();
                    child.layout(curL, curT, curR, curB);
                    curT += child.getMeasuredHeight() + lp.bottomMargin;
                    break;
                default:
                    curL = getPaddingStart() + lp.leftMargin;
                    curR = curL + child.getMeasuredWidth();
                    curT += lp.topMargin;
                    curB = curT + child.getMeasuredHeight();
                    child.layout(curL, curT, curR, curB);
                    curT += child.getMeasuredHeight() + lp.bottomMargin;
                    break;
            }
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
