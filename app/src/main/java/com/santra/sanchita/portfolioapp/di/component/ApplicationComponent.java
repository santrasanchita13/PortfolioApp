package com.santra.sanchita.portfolioapp.di.component;

import android.app.Application;
import android.content.Context;

import com.santra.sanchita.portfolioapp.MvpApp;
import com.santra.sanchita.portfolioapp.data.DataManager;
import com.santra.sanchita.portfolioapp.di.ApplicationContext;
import com.santra.sanchita.portfolioapp.di.module.ApplicationModule;
import com.santra.sanchita.portfolioapp.service.SyncService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by sanchita on 6/12/17.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MvpApp mvpApp);

    void inject(SyncService syncService);

    @ApplicationContext
    Context context();

    Application application();

    DataManager dataManager();
}

