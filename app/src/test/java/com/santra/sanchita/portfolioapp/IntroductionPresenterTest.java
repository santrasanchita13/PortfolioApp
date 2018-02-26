package com.santra.sanchita.portfolioapp;

import android.os.Bundle;

import com.santra.sanchita.portfolioapp.data.DataManager;
import com.santra.sanchita.portfolioapp.data.db.model.DesignItem;
import com.santra.sanchita.portfolioapp.ui.introduction.IntroductionMvpView;
import com.santra.sanchita.portfolioapp.ui.introduction.IntroductionPresenter;
import com.santra.sanchita.portfolioapp.utils.Constants;
import com.santra.sanchita.portfolioapp.utils.rx.TestSchedulerProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

/**
 * Created by sanchita on 29/12/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class IntroductionPresenterTest {
    @Mock
    IntroductionMvpView view;

    @Mock
    DataManager dataManager;

    private TestScheduler testScheduler;

    private IntroductionPresenter<IntroductionMvpView> presenter;

    private CompositeDisposable compositeDisposable;

    @BeforeClass
    public static void onlyOnce() throws Exception {
    }

    @Before
    public void setUpSplashPresenter() {
        compositeDisposable = new CompositeDisposable();
        testScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(testScheduler);
        presenter = new IntroductionPresenter<>(dataManager, testSchedulerProvider, compositeDisposable);
        presenter.onAttach(view);
    }

    @Test
    public void getDetailsData() {

        DesignItem designItem = new DesignItem(0L, null, "Forms1",
                R.drawable.forms1 + "", "Few prototype forms you may like", false);

        /*
        In case of synchronous callback do this
         */
        doReturn(Observable.just(designItem))
                .when(dataManager)
                .getDesignItemById(0L);

        Bundle bundle = new Bundle();
        bundle.putInt(Constants.POSITION, 0);

        presenter.onViewPrepared(bundle);

        testScheduler.triggerActions();

        /*
        In case of asynchronous callback do this

        presenter.onViewPrepared();

        verify(dataManager.getAllDesignItems()
                .subscribeOn(testScheduler)
                .observeOn(testScheduler)).subscribe(designItemsCallbackCaptor.capture());

        designItemsCallbackCaptor.getValue().accept(designItemList);*/

        verify(view).updateDetails(designItem);
    }

    @After
    public void tearDown() throws Exception {
        presenter.onDetach();
    }
}
