package com.santra.sanchita.portfolioapp.ui.design_list;

import com.santra.sanchita.portfolioapp.R;
import com.santra.sanchita.portfolioapp.data.DataManager;
import com.santra.sanchita.portfolioapp.ui.base.BasePresenter;
import com.santra.sanchita.portfolioapp.utils.EspressoIdlingResource;
import com.santra.sanchita.portfolioapp.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by sanchita on 13/12/17.
 */

public class DesignListPresenter<V extends DesignListMvpView> extends BasePresenter<V> implements DesignListMvpPresenter<V> {

    @Inject
    public DesignListPresenter(DataManager dataManager,
                            SchedulerProvider schedulerProvider,
                            CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
    }

    @Override
    public void onViewPrepared() {
        EspressoIdlingResource.increment();
        getCompositeDisposable().add(getDataManager()
                .getDesignItemsCount()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(count -> getMvpView().updateDetails(count),
                        throwable -> {
                            if(!isViewAttached()) {
                                return;
                            }
                            getMvpView().onError(R.string.default_error);
                        }));
    }
}
