package com.santra.sanchita.portfolioapp.ui.custom_views;

import android.content.Context;
import android.graphics.Outline;
import android.os.Build;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;

import com.santra.sanchita.portfolioapp.R;

/**
 * Created by sanchita on 14/12/17.
 */

public class RoundedImageView extends AppCompatImageView {

    public RoundedImageView(Context context) {
        super(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setClipToOutline(true);
            setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        outline.setRoundRect(0,0,view.getWidth(), view.getHeight(), getResources().getDimension(R.dimen.rounded_corners));
                    }
                }
            });
        }
    }

    public RoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setClipToOutline(true);
            setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        outline.setRoundRect(0,0,view.getWidth(), view.getHeight(), getResources().getDimension(R.dimen.rounded_corners));
                    }
                }
            });
        }
    }

    public RoundedImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setClipToOutline(true);
            setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        outline.setRoundRect(0,0,view.getWidth(), view.getHeight(), getResources().getDimension(R.dimen.rounded_corners));
                    }
                }
            });
        }
    }
}
