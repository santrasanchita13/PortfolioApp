package com.santra.sanchita.portfolioapp.di.component;

import com.santra.sanchita.portfolioapp.di.PerActivity;
import com.santra.sanchita.portfolioapp.di.module.ActivityModule;
import com.santra.sanchita.portfolioapp.ui.checkout_form.CheckoutFormActivity;
import com.santra.sanchita.portfolioapp.ui.checkout_form.checkout_form1.CheckoutForm1Fragment;
import com.santra.sanchita.portfolioapp.ui.checkout_form.checkout_form2.CheckoutForm2Fragment;
import com.santra.sanchita.portfolioapp.ui.checkout_form.checkout_form3.CheckoutForm3Fragment;
import com.santra.sanchita.portfolioapp.ui.checkout_form.checkout_form4.CheckoutForm4Fragment;
import com.santra.sanchita.portfolioapp.ui.contact.ContactActivity;
import com.santra.sanchita.portfolioapp.ui.design_details.DesignDetailsActivity;
import com.santra.sanchita.portfolioapp.ui.design_item.DesignItemFragment;
import com.santra.sanchita.portfolioapp.ui.design_list.DesignListFragment;
import com.santra.sanchita.portfolioapp.ui.introduction.IntroductionActivity;
import com.santra.sanchita.portfolioapp.ui.main.MainActivity;
import com.santra.sanchita.portfolioapp.ui.skills.SkillsActivity;
import com.santra.sanchita.portfolioapp.ui.splash.SplashActivity;

import dagger.Component;

/**
 * Created by sanchita on 6/12/17.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(SplashActivity activity);

    void inject(MainActivity activity);

    void inject(DesignListFragment fragment);

    void inject(DesignItemFragment fragment);

    void inject(DesignDetailsActivity activity);

    void inject(CheckoutFormActivity activity);

    void inject(CheckoutForm1Fragment fragment);

    void inject(CheckoutForm2Fragment fragment);

    void inject(CheckoutForm3Fragment fragment);

    void inject(CheckoutForm4Fragment fragment);

    void inject(IntroductionActivity activity);

    void inject(SkillsActivity activity);

    void inject(ContactActivity activity);
}
