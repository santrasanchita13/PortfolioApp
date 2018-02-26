package com.santra.sanchita.portfolioapp.ui.design_item;

import android.os.Bundle;

import com.santra.sanchita.portfolioapp.ui.base.MvpPresenter;

/**
 * Created by sanchita on 13/12/17.
 */

public interface DesignItemMvpPresenter<V extends DesignItemMvpView> extends MvpPresenter<V> {

    void onViewPrepared(Bundle extras);
}
