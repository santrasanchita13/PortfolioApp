package com.santra.sanchita.portfolioapp.ui.introduction;

import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.santra.sanchita.portfolioapp.R;
import com.santra.sanchita.portfolioapp.data.DataManager;
import com.santra.sanchita.portfolioapp.ui.base.BasePresenter;
import com.santra.sanchita.portfolioapp.utils.Constants;
import com.santra.sanchita.portfolioapp.utils.EspressoIdlingResource;
import com.santra.sanchita.portfolioapp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by sanchita on 25/12/17.
 */

public class IntroductionPresenter<V extends IntroductionMvpView> extends BasePresenter<V> implements IntroductionMvpPresenter<V> {

    @Inject
    public IntroductionPresenter(DataManager dataManager,
                         SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
    }

    @Override
    public void onViewPrepared(Bundle extras) {
        EspressoIdlingResource.increment();
        getCompositeDisposable().add(getDataManager()
                .getDesignItemById((long) extras.getInt(Constants.POSITION))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(designItem -> getMvpView().updateDetails(designItem),
                        throwable -> {
                            if(!isViewAttached()) {
                                return;
                            }
                            getMvpView().onError(R.string.default_error);
                        }));
    }

    @Override
    public void sendEmail(GoogleSignInAccount account, String message) {

        getMvpView().showLoading();

        EspressoIdlingResource.increment();
        getCompositeDisposable().add(getDataManager()
                .sendEmail(account, message)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(emailSent -> {

                            getMvpView().hideLoading();

                            if(emailSent) {
                                getMvpView().emailSent(true);
                            }
                            else {
                                getMvpView().emailSent(false);
                            }
                            //getMvpView().showMessage(emailSent);
                        },
                        throwable -> {

                            getMvpView().hideLoading();

                            if(!isViewAttached()) {
                                return;
                            }
                            getMvpView().emailSent(false);
                            //getMvpView().showMessage(throwable.getMessage());
                        }));
    }
}