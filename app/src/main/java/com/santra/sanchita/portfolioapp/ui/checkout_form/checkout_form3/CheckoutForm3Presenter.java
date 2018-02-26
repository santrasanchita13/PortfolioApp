package com.santra.sanchita.portfolioapp.ui.checkout_form.checkout_form3;

import com.santra.sanchita.portfolioapp.data.DataManager;
import com.santra.sanchita.portfolioapp.ui.base.BasePresenter;
import com.santra.sanchita.portfolioapp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by sanchita on 19/12/17.
 */

public class CheckoutForm3Presenter<V extends CheckoutForm3MvpView> extends BasePresenter<V> implements CheckoutForm3MvpPresenter<V> {

    @Inject
    public CheckoutForm3Presenter(DataManager dataManager,
                                  SchedulerProvider schedulerProvider,
                                  CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
    }
}
