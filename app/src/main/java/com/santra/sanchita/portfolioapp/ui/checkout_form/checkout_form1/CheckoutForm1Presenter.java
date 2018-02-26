package com.santra.sanchita.portfolioapp.ui.checkout_form.checkout_form1;

import com.santra.sanchita.portfolioapp.data.DataManager;
import com.santra.sanchita.portfolioapp.ui.base.BasePresenter;
import com.santra.sanchita.portfolioapp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by sanchita on 19/12/17.
 */

public class CheckoutForm1Presenter<V extends CheckoutForm1MvpView> extends BasePresenter<V> implements CheckoutForm1MvpPresenter<V> {

    @Inject
    public CheckoutForm1Presenter(DataManager dataManager,
                               SchedulerProvider schedulerProvider,
                               CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
    }
}
