package com.santra.sanchita.portfolioapp.ui.design_item;

import android.os.Bundle;

import com.santra.sanchita.portfolioapp.R;
import com.santra.sanchita.portfolioapp.data.DataManager;
import com.santra.sanchita.portfolioapp.ui.base.BasePresenter;
import com.santra.sanchita.portfolioapp.utils.Constants;
import com.santra.sanchita.portfolioapp.utils.EspressoIdlingResource;
import com.santra.sanchita.portfolioapp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by sanchita on 13/12/17.
 */

public class DesignItemPresenter<V extends DesignItemMvpView> extends BasePresenter<V> implements DesignItemMvpPresenter<V> {

    @Inject
    public DesignItemPresenter(DataManager dataManager,
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
}

