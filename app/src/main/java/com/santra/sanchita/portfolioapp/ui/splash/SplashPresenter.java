package com.santra.sanchita.portfolioapp.ui.splash;

import com.santra.sanchita.portfolioapp.R;
import com.santra.sanchita.portfolioapp.data.DataManager;
import com.santra.sanchita.portfolioapp.ui.base.BasePresenter;
import com.santra.sanchita.portfolioapp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by sanchita on 8/12/17.
 */

public class SplashPresenter<V extends SplashMvpView> extends BasePresenter<V> implements SplashMvpPresenter<V> {

    @Inject
    public SplashPresenter(DataManager dataManager,
                           SchedulerProvider schedulerProvider,
                           CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);

        getMvpView().startSyncService();

        getCompositeDisposable().add(getDataManager()
            .seedDesignItems()
            .subscribeOn(getSchedulerProvider().io())
            .observeOn(getSchedulerProvider().ui())
            .subscribe(aBoolean -> {
                if (!isViewAttached()) {
                    return;
                }
                nextActivity();
            }, throwable -> {
                if(!isViewAttached()) {
                    return;
                }
                getMvpView().onError(R.string.default_error);
                nextActivity();
            }));
    }

    private void nextActivity() {
        getMvpView().openMainActivity();
    }
}
