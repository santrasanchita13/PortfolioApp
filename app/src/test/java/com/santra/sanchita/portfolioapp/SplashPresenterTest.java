package com.santra.sanchita.portfolioapp;

import com.santra.sanchita.portfolioapp.data.DataManager;
import com.santra.sanchita.portfolioapp.ui.splash.SplashMvpView;
import com.santra.sanchita.portfolioapp.ui.splash.SplashPresenter;
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
 * Created by sanchita on 11/12/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class SplashPresenterTest {

    @Mock
    SplashMvpView view;

    @Mock
    DataManager dataManager;

    private TestScheduler testScheduler;

    private SplashPresenter<SplashMvpView> presenter;

    @BeforeClass
    public static void onlyOnce() throws Exception {
    }

    @Before
    public void setUpSplashPresenter() {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        testScheduler = new TestScheduler();
        TestSchedulerProvider testSchedulerProvider = new TestSchedulerProvider(testScheduler);
        presenter = new SplashPresenter<>(dataManager, testSchedulerProvider, compositeDisposable);
    }

    @Test
    public void loadApp_InitiateDb() {

        doReturn(Observable.just(true))
                .when(dataManager)
                .seedDesignItems();

        presenter.onAttach(view);

        testScheduler.triggerActions();

        verify(view).openMainActivity();
    }

    @After
    public void tearDown() throws Exception {
        presenter.onDetach();
    }
}
