package com.santra.sanchita.portfolioapp.ui.design_list;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by sanchita on 15/12/17.
 */

public class ZoomFadeTransformer implements ViewPager.PageTransformer {

    private float offset = -1;
    private float paddingLeft;
    private float minScale;
    private float minAlpha;

    public ZoomFadeTransformer(float paddingLeft, float minScale, float minAlpha) {
        this.paddingLeft = paddingLeft;
        this.minAlpha = minAlpha;
        this.minScale = minScale;
    }

    @Override
    public void transformPage(View page, float position) {
        if (offset == -1) {
            offset = paddingLeft / page.getMeasuredWidth();
        }
        if (position < -1) {
            page.setAlpha(0);
        } else {
            float scaleFactor = Math.max(minScale, 1 - Math.abs(position - offset));
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
            float alphaFactor = Math.max(minAlpha, 1 - Math.abs(position - offset));
            page.setAlpha(alphaFactor);
        }
    }
}