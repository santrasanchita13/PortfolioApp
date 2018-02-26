package com.santra.sanchita.portfolioapp.ui.base;

/**
 * Created by sanchita on 6/12/17.
 */

public interface MvpPresenter<V extends MvpView> {
    void onAttach(V mvpView);

    void onDetach();

    void handleApiError(Throwable error);

    void setUserAsLoggedOut();
}
