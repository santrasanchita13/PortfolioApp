package com.santra.sanchita.portfolioapp.ui.base;

/**
 * Created by sanchita on 6/12/17.
 */

public interface SubMvpView extends MvpView {
    void onCreate();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void attachParentMvpView(MvpView mvpView);
}
