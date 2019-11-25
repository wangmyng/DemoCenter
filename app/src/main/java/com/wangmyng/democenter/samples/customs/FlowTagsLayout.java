package com.wangmyng.democenter.samples.customs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

public class FlowTagsLayout extends ViewGroup {

    public FlowTagsLayout(Context context) {
        super(context);
    }

    public FlowTagsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowTagsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //创建基础变量
        int measuredWidth = 0;
        int measuredHeight = 0;
        int widthMeasureSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMeasureSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        //当matchParent或已设定具体数值时跳过测量
        LayoutParams lp = getLayoutParams();
        if (lp.width == LayoutParams.MATCH_PARENT) {
            measuredWidth = widthMeasureSpecSize;
        }
        if (lp.width > 0) {
            measuredWidth = lp.width;
        }
        if (lp.height == LayoutParams.MATCH_PARENT) {
            measuredHeight = heightMeasureSpecSize;
        }
        if (lp.height > 0) {
            measuredHeight = lp.height;
        }
        boolean skipMeasureWidth = measuredWidth != 0;
        boolean skipMeasureHeight = measuredHeight != 0;

        //遍历测量子View
        View child;
        int remainingWidth = widthMeasureSpecSize;
        int lineMaxWidth = 0;
        int lineMaxHeight = 0;
        MarginLayoutParams mLp;
        for (int i = 0; i < getChildCount(); i++) {
            child = getChildAt(i);
            mLp = (MarginLayoutParams) child.getLayoutParams();
            measureChild(child,
                    getChildMeasureSpec(widthMeasureSpec, getPaddingLeft() + getPaddingRight(), mLp.width),
                    getChildMeasureSpec(heightMeasureSpec, getPaddingTop() + getPaddingBottom(), mLp.height));
            int childNeedWidth = child.getMeasuredWidth() + mLp.leftMargin + mLp.rightMargin;
            int childNeedHeight = child.getMeasuredHeight() + mLp.topMargin + mLp.bottomMargin;
            if (remainingWidth > childNeedWidth) {
                remainingWidth -= childNeedWidth;
                lineMaxWidth += childNeedWidth;
                lineMaxHeight = Math.max(lineMaxHeight, childNeedHeight);
            } else {
                if (!skipMeasureWidth)
                    measuredWidth = Math.max(measuredWidth, lineMaxWidth);
                if (!skipMeasureHeight)
                    measuredHeight += lineMaxHeight;
                remainingWidth = widthMeasureSpecSize;
                lineMaxWidth = 0;
            }
        }

        //遍历时未处理最后一行宽高
        if (!skipMeasureWidth)
            measuredWidth = Math.max(measuredWidth, lineMaxWidth) + getPaddingStart() + getPaddingEnd();
        if (!skipMeasureHeight)
            measuredHeight += lineMaxHeight + getPaddingTop() + getPaddingBottom();

        //测量完成
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int curL, curT, curR, curB;
        curL = getPaddingLeft();
        curT = getPaddingTop();
        int remainingWidth = getMeasuredWidth();
        View child;
        MarginLayoutParams lp;
        int lastLineHeight = 0;
        int lastLineMarginBottom = 0;
        for (int i = 0; i < getChildCount(); i++) {
            child = getChildAt(i);
            lp = (MarginLayoutParams) child.getLayoutParams();
            int childNeedWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            if (i == 0) {
                curT += lp.topMargin;
            }
            if (remainingWidth > childNeedWidth) {
                remainingWidth -= childNeedWidth;
                curL += lp.leftMargin;
                curR = curL + child.getMeasuredWidth();
                curB = curT + child.getMeasuredHeight();
                child.layout(curL, curT, curR, curB);
                curL = curR + lp.rightMargin;
                lastLineMarginBottom = lp.bottomMargin;
                lastLineHeight = child.getMeasuredHeight();
            } else {
                remainingWidth = getMeasuredWidth() - childNeedWidth;
                curL = getPaddingLeft() + lp.leftMargin;
                curR = curL + child.getMeasuredWidth();
                curT += lastLineHeight + lastLineMarginBottom + lp.topMargin;
                curB = curT + child.getMeasuredHeight();
                child.layout(curL, curT, curR, curB);
                curL = curR + lp.rightMargin;
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
