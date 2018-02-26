package com.santra.sanchita.portfolioapp.ui.design_list;

import com.santra.sanchita.portfolioapp.ui.base.MvpPresenter;

/**
 * Created by sanchita on 13/12/17.
 */

public interface DesignListMvpPresenter<V extends DesignListMvpView> extends MvpPresenter<V> {

    void onViewPrepared();
}