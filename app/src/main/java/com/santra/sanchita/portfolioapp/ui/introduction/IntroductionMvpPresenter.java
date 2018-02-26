package com.santra.sanchita.portfolioapp.ui.introduction;

import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.santra.sanchita.portfolioapp.ui.base.MvpPresenter;

/**
 * Created by sanchita on 25/12/17.
 */

public interface IntroductionMvpPresenter<V extends IntroductionMvpView> extends MvpPresenter<V> {
    void onViewPrepared(Bundle extras);

    void sendEmail(GoogleSignInAccount account, String message);
}
