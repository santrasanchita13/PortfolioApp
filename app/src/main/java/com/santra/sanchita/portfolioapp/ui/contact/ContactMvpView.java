package com.santra.sanchita.portfolioapp.ui.contact;

import com.santra.sanchita.portfolioapp.data.db.model.DesignItem;
import com.santra.sanchita.portfolioapp.ui.base.MvpView;

/**
 * Created by sanchita on 25/1/18.
 */

public interface ContactMvpView extends MvpView {

    void updateDetails(DesignItem designItem);

    void checkSignIn();

    void emailSent(boolean sent);

    boolean deleteContact(String phone, String name);

    void callMe();
}