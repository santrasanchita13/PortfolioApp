package com.santra.sanchita.portfolioapp.ui.base;

import android.support.annotation.StringRes;

/**
 * Created by sanchita on 6/12/17.
 */

public interface MvpView {

    void hideActionBar();

    void showLoading();

    void hideLoading();

    void onError(@StringRes int resId);

    void onError(String message);

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    void showPopUpExplanation(String message, int gravity);

    boolean isNetworkConnected();

    void hideKeyboard();
}
