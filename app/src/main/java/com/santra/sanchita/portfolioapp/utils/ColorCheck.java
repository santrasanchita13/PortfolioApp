package com.santra.sanchita.portfolioapp.utils;

import android.graphics.Color;

/**
 * Created by sanchita on 16/12/17.
 */

public class ColorCheck {
    public static boolean isColorDark(int color){
        double darkness = 1-(0.299* Color.red(color) + 0.587*Color.green(color) + 0.114*Color.blue(color))/255;
        return !(darkness < 0.5);
    }
}
