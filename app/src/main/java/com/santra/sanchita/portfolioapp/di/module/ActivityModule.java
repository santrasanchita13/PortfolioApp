package com.santra.sanchita.portfolioapp.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.santra.sanchita.portfolioapp.di.ActivityContext;
import com.santra.sanchita.portfolioapp.di.PerActivity;
import com.santra.sanchita.portfolioapp.ui.checkout_form.CheckoutFormMvpPresenter;
import com.santra.sanchita.portfolioapp.ui.checkout_form.CheckoutFormMvpView;
import com.santra.sanchita.portfolioapp.ui.checkout_form.CheckoutFormPagerAdapter;
import com.santra.sanchita.portfolioapp.ui.checkout_form.CheckoutFormPresenter;
import com.santra.sanchita.portfolioapp.ui.checkout_form.checkout_form1.CheckoutForm1MvpPresenter;
import com.santra.sanchita.portfolioapp.ui.checkout_form.checkout_form1.CheckoutForm1MvpView;
import com.santra.sanchita.portfolioapp.ui.checkout_form.checkout_form1.CheckoutForm1Presenter;
import com.santra.sanchita.portfolioapp.ui.checkout_form.checkout_form2.CheckoutForm2MvpPresenter;
import com.santra.sanchita.portfolioapp.ui.checkout_form.checkout_form2.CheckoutForm2MvpView;
import com.santra.sanchita.portfolioapp.ui.checkout_form.checkout_form2.CheckoutForm2Presenter;
import com.santra.sanchita.portfolioapp.ui.checkout_form.checkout_form3.CheckoutForm3MvpPresenter;
import com.santra.sanchita.portfolioapp.ui.checkout_form.checkout_form3.CheckoutForm3MvpView;
import com.santra.sanchita.portfolioapp.ui.checkout_form.checkout_form3.CheckoutForm3Presenter;
import com.santra.sanchita.portfolioapp.ui.checkout_form.checkout_form4.CheckoutForm4MvpPresenter;
import com.santra.sanchita.portfolioapp.ui.checkout_form.checkout_form4.CheckoutForm4MvpView;
import com.santra.sanchita.portfolioapp.ui.checkout_form.checkout_form4.CheckoutForm4Presenter;
import com.santra.sanchita.portfolioapp.ui.contact.ContactMvpPresenter;
import com.santra.sanchita.portfolioapp.ui.contact.ContactMvpView;
import com.santra.sanchita.portfolioapp.ui.contact.ContactPresenter;
import com.santra.sanchita.portfolioapp.ui.design_details.DesignDetailsMvpPresenter;
import com.santra.sanchita.portfolioapp.ui.design_details.DesignDetailsMvpView;
import com.santra.sanchita.portfolioapp.ui.design_details.DesignDetailsPresenter;
import com.santra.sanchita.portfolioapp.ui.design_item.DesignItemMvpPresenter;
import com.santra.sanchita.portfolioapp.ui.design_item.DesignItemMvpView;
import com.santra.sanchita.portfolioapp.ui.design_item.DesignItemPresenter;
import com.santra.sanchita.portfolioapp.ui.design_list.DesignListMvpPresenter;
import com.santra.sanchita.portfolioapp.ui.design_list.DesignListMvpView;
import com.santra.sanchita.portfolioapp.ui.design_list.DesignListPagerAdapter;
import com.santra.sanchita.portfolioapp.ui.design_list.DesignListPresenter;
import com.santra.sanchita.portfolioapp.ui.introduction.IntroductionMvpPresenter;
import com.santra.sanchita.portfolioapp.ui.introduction.IntroductionMvpView;
import com.santra.sanchita.portfolioapp.ui.introduction.IntroductionPresenter;
import com.santra.sanchita.portfolioapp.ui.main.MainMvpPresenter;
import com.santra.sanchita.portfolioapp.ui.main.MainMvpView;
import com.santra.sanchita.portfolioapp.ui.main.MainPresenter;
import com.santra.sanchita.portfolioapp.ui.skills.SkillsMvpPresenter;
import com.santra.sanchita.portfolioapp.ui.skills.SkillsMvpView;
import com.santra.sanchita.portfolioapp.ui.skills.SkillsPresenter;
import com.santra.sanchita.portfolioapp.ui.splash.SplashMvpPresenter;
import com.santra.sanchita.portfolioapp.ui.splash.SplashMvpView;
import com.santra.sanchita.portfolioapp.ui.splash.SplashPresenter;
import com.santra.sanchita.portfolioapp.utils.rx.AppSchedulerProvider;
import com.santra.sanchita.portfolioapp.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by sanchita on 6/12/17.
 */

@Module
public class ActivityModule {
    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @PerActivity
    SplashMvpPresenter<SplashMvpView> provideSplashPresenter(SplashPresenter<SplashMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    MainMvpPresenter<MainMvpView> provideMainPresenter(MainPresenter<MainMvpView> presenter) {
        return presenter;
    }

    @Provides
    DesignListMvpPresenter<DesignListMvpView> provideDesignListPresenter(DesignListPresenter<DesignListMvpView> presenter) {
        return presenter;
    }

    @Provides
    DesignItemMvpPresenter<DesignItemMvpView> providesDesignItemPresenter(DesignItemPresenter<DesignItemMvpView> presenter) {
        return presenter;
    }

    @Provides
    DesignListPagerAdapter providesDesignListPagerAdapter(AppCompatActivity activity) {
        return new DesignListPagerAdapter(activity.getSupportFragmentManager());
    }

    @Provides
    @PerActivity
    DesignDetailsMvpPresenter<DesignDetailsMvpView> provideDesignDetailsPresenter(DesignDetailsPresenter<DesignDetailsMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    CheckoutFormMvpPresenter<CheckoutFormMvpView> provideCheckoutFormPresenter(CheckoutFormPresenter<CheckoutFormMvpView> presenter) {
        return presenter;
    }

    @Provides
    CheckoutForm1MvpPresenter<CheckoutForm1MvpView> providesCheckoutForm1Presenter(CheckoutForm1Presenter<CheckoutForm1MvpView> presenter) {
        return presenter;
    }

    @Provides
    CheckoutForm2MvpPresenter<CheckoutForm2MvpView> providesCheckoutForm2Presenter(CheckoutForm2Presenter<CheckoutForm2MvpView> presenter) {
        return presenter;
    }

    @Provides
    CheckoutForm3MvpPresenter<CheckoutForm3MvpView> providesCheckoutForm3Presenter(CheckoutForm3Presenter<CheckoutForm3MvpView> presenter) {
        return presenter;
    }

    @Provides
    CheckoutForm4MvpPresenter<CheckoutForm4MvpView> providesCheckoutForm4Presenter(CheckoutForm4Presenter<CheckoutForm4MvpView> presenter) {
        return presenter;
    }

    @Provides
    CheckoutFormPagerAdapter providesCheckoutFormPagerAdapter(AppCompatActivity activity) {
        return new CheckoutFormPagerAdapter(activity.getSupportFragmentManager());
    }

    @Provides
    @PerActivity
    IntroductionMvpPresenter<IntroductionMvpView> provideIntroductionPresenter(IntroductionPresenter<IntroductionMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    SkillsMvpPresenter<SkillsMvpView> providesSkillsPresenter(SkillsPresenter<SkillsMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ContactMvpPresenter<ContactMvpView> provideContactPresenter(ContactPresenter<ContactMvpView> presenter) {
        return presenter;
    }
}
