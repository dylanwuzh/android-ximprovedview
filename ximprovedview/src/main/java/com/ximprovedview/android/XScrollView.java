package com.ximprovedview.android;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * ScrollView 的增强控件。
 *
 * @author wuzhen
 * @version Version 1.0, 2016-09-14
 */
public class XScrollView extends ScrollView {

    private static final int MIN_SCROLL_SCALE_FACTOR = 2;
    private static final int MAX_SCROLL_SCALE_FACTOR = 4;

    private static final int DEFAULT_BOUNCE_MAX_SCROLL_DP = 200;

    private int mMaxHeight;
    private int mBounceMaxScroll;
    private boolean mBounceEnabled;

    private int originOverScrollMode;
    private float deltaY;
    private float bounceY;
    private boolean isCount;
    private View inner;

    private Rect normal = new Rect();

    public XScrollView(Context context) {
        super(context);

        init(context, null, 0);
    }

    public XScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs, 0);
    }

    public XScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.XScrollView, defStyleAttr, 0);
        mMaxHeight = a.getDimensionPixelOffset(R.styleable.XScrollView_android_maxHeight, -1);
        mBounceMaxScroll = a.getDimensionPixelOffset(R.styleable.XScrollView_xsv_bounceMaxScroll,
                dp2px(context, DEFAULT_BOUNCE_MAX_SCROLL_DP));
        boolean bounceEnabled = a.getBoolean(R.styleable.XScrollView_xsv_bounceEnabled, false);
        a.recycle();

        originOverScrollMode = getOverScrollMode();
        setBounceEnabled(bounceEnabled);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mMaxHeight > 0) {
            try {
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxHeight,
                        MeasureSpec.AT_MOST);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 设置最大高度。
     *
     * @param heightPixel 最大高度，单位：像素
     */
    public void setMaxViewHeight(int heightPixel) {
        this.mMaxHeight = heightPixel;
        requestLayout();
    }

    /**
     * 设置是否支持回弹。
     *
     * @param enabled 是否支持回弹
     */
    public void setBounceEnabled(boolean enabled) {
        this.mBounceEnabled = enabled;
        if (enabled) {
            super.setOverScrollMode(ScrollView.OVER_SCROLL_NEVER);
        } else {
            super.setOverScrollMode(originOverScrollMode);
        }
    }

    @Override
    public void setOverScrollMode(int mode) {
        super.setOverScrollMode(mode);

        originOverScrollMode = mode;
    }

    @Override
    protected void onFinishInflate() {
        if (getChildCount() > 0) {
            inner = getChildAt(0);
        }

        super.onFinishInflate();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = super.onInterceptTouchEvent(ev);

        if (mBounceEnabled) {
            intercepted = (ev.getAction() == MotionEvent.ACTION_MOVE);
        }
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mBounceEnabled && inner != null) {
            handleBounceTouchEvent(ev);
        }

        return super.onTouchEvent(ev);
    }

    private void handleBounceTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                if (!normal.isEmpty()) {
                    startResumeAnimation();
                    isCount = false;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                float preY = bounceY;
                float nowY = ev.getY();
                int maxScroll = Math.min(mBounceMaxScroll, getHeight() / 3);
                float scale = MIN_SCROLL_SCALE_FACTOR + (MAX_SCROLL_SCALE_FACTOR - MIN_SCROLL_SCALE_FACTOR)
                        * (Math.abs(inner.getTop()) * 1.f / maxScroll);
                deltaY += (preY - nowY) / scale;

                if (!isCount) {
                    deltaY = 0;
                }
                bounceY = nowY;

                if (isNeedBounceScroll()) {
                    if (normal.isEmpty()) {
                        normal.set(inner.getLeft(), inner.getTop(),
                                inner.getRight(), inner.getBottom());
                    }

                    if (Math.abs(deltaY) >= 1) {
                        inner.layout(inner.getLeft(), inner.getTop() - (int) deltaY,
                                inner.getRight(), inner.getBottom() - (int) deltaY);
                        deltaY = deltaY % 1;
                    }
                }
                isCount = true;
                break;

            default:
                break;
        }
    }

    private void startResumeAnimation() {
        TranslateAnimation animation = new TranslateAnimation(0, 0, inner.getTop(), normal.top);
        animation.setInterpolator(new DecelerateInterpolator(1.5f));
        animation.setDuration(200);
        inner.startAnimation(animation);
        inner.layout(normal.left, normal.top, normal.right, normal.bottom);
        normal.setEmpty();
    }

    private boolean isNeedBounceScroll() {
        int offset = inner.getMeasuredHeight() - getHeight();
        int scrollY = getScrollY();
        return scrollY == 0 || scrollY == offset;
    }

    private static int dp2px(Context context, float dpValue) {
        float value = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue,
                context.getResources().getDisplayMetrics());
        return (int) (value + 0.5f);
    }
}