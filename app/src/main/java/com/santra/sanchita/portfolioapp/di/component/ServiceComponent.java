package com.santra.sanchita.portfolioapp.di.component;

import com.santra.sanchita.portfolioapp.di.PerService;
import com.santra.sanchita.portfolioapp.di.module.ServiceModule;
import com.santra.sanchita.portfolioapp.service.SyncService;

import dagger.Component;

/**
 * Created by sanchita on 21/11/17.
 */

@PerService
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {
    void inject(SyncService syncService);
}
