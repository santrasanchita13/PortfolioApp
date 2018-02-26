package com.santra.sanchita.portfolioapp.ui.design_details;

import android.os.Bundle;

import com.santra.sanchita.portfolioapp.ui.base.MvpPresenter;

/**
 * Created by sanchita on 15/12/17.
 */

public interface DesignDetailsMvpPresenter<V extends DesignDetailsMvpView> extends MvpPresenter<V> {
    void onViewAttached(Bundle extras);
}
