package com.santra.sanchita.portfolioapp;

import com.santra.sanchita.portfolioapp.data.DataManager;
import com.santra.sanchita.portfolioapp.ui.design_list.DesignListMvpView;
import com.santra.sanchita.portfolioapp.ui.design_list.DesignListPresenter;
import com.santra.sanchita.portfolioapp.utils.rx.TestSchedulerProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

/**
 * Created by sanchita on 18/12/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class DesignListPresenterTest {

    @Mock
    DesignListMvpView view;

    @Mock
    DataManager dataManager;

    private TestScheduler testScheduler;

    private DesignListPresenter<DesignListMvpView> presenter;

    private CompositeDisposable compositeDisposable;

    @Captor
    private ArgumentCaptor<Consumer<Long>> designItemsCountCallbackCaptor;

    @BeforeClass
    public static void onlyOnce() throws Exception {
    }

    @Before
    public void setUpSplashPresenter() {
        compositeDisposable = new CompositeDisposable();
        testScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(testScheduler);
        presenter = new DesignListPresenter<>(dataManager, testSchedulerProvider, compositeDisposable);
        presenter.onAttach(view);
    }

    @Test
    public void getDetailsData() {

        /*
        In case of synchronous callback do this
         */
        doReturn(Observable.just(3L))
                .when(dataManager)
                .getDesignItemsCount();

        presenter.onViewPrepared();

        testScheduler.triggerActions();

        /*
        In case of asynchronous callback do this

        presenter.onViewPrepared();

        verify(dataManager.getAllDesignItems()
                .subscribeOn(testScheduler)
                .observeOn(testScheduler)).subscribe(designItemsCallbackCaptor.capture());

        designItemsCallbackCaptor.getValue().accept(designItemList);*/

        verify(view).updateDetails(3L);
    }

    @After
    public void tearDown() throws Exception {
        presenter.onDetach();
    }
}
