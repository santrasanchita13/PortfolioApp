package com.santra.sanchita.portfolioapp.di.module;

import android.app.Service;

import dagger.Module;

/**
 * Created by sanchita on 6/12/17.
 */

@Module
public class ServiceModule {
    private final Service mService;

    public ServiceModule(Service service) {
        mService = service;
    }
}
