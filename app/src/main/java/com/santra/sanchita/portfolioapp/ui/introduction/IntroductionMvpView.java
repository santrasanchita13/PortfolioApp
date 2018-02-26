package com.santra.sanchita.portfolioapp.ui.introduction;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.santra.sanchita.portfolioapp.data.db.model.DesignItem;
import com.santra.sanchita.portfolioapp.ui.base.MvpView;

/**
 * Created by sanchita on 25/12/17.
 */

public interface IntroductionMvpView extends MvpView {
    int VIEW_TYPE_PROFILE = 0;
    int VIEW_TYPE_ABOUT = 1;

    void updateDetails(DesignItem designItem);

    void callMe();

    void showPopUpEmail(GoogleSignInAccount account);

    boolean deleteContact(String phone, String name);

    void checkSignIn();

    void emailSent(boolean sent);
}
