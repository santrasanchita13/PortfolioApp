package com.santra.sanchita.portfolioapp;

import android.support.multidex.MultiDexApplication;

import com.santra.sanchita.portfolioapp.data.DataManager;
import com.santra.sanchita.portfolioapp.di.component.ApplicationComponent;
import com.santra.sanchita.portfolioapp.di.component.DaggerApplicationComponent;
import com.santra.sanchita.portfolioapp.di.module.ApplicationModule;
import com.santra.sanchita.portfolioapp.utils.AppLogger;

import javax.inject.Inject;

/**
 * Created by sanchita on 20/11/17.
 */

public class MvpApp extends MultiDexApplication {

    @Inject
    DataManager dataManager;

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

        applicationComponent.inject(this);

        AppLogger.init();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public void setApplicationComponent(ApplicationComponent component) {
        applicationComponent = component;
    }
}
