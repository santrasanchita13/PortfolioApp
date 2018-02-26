package com.santra.sanchita.portfolioapp.ui.custom_views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by sanchita on 20/12/17.
 */

public class OptionalSwipeViewPager extends ViewPager {

    private boolean isPagingEnabled = true;

    public OptionalSwipeViewPager(Context context) {
        super(context);
    }

    public OptionalSwipeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onInterceptTouchEvent(event);
    }

    public void setPagingEnabled(boolean b) {
        this.isPagingEnabled = b;
    }
}