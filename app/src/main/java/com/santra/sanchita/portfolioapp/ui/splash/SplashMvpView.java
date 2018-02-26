package com.santra.sanchita.portfolioapp.ui.splash;

import com.santra.sanchita.portfolioapp.ui.base.MvpView;

/**
 * Created by sanchita on 8/12/17.
 */

public interface SplashMvpView extends MvpView {

    void openMainActivity();

    void startSyncService();
}
