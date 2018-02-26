package com.santra.sanchita.portfolioapp.ui.contact;

import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.santra.sanchita.portfolioapp.ui.base.MvpPresenter;

/**
 * Created by sanchita on 25/1/18.
 */

public interface ContactMvpPresenter<V extends ContactMvpView> extends MvpPresenter<V> {
    void onViewPrepared(Bundle extras);

    void sendEmail(GoogleSignInAccount account, String message);
}