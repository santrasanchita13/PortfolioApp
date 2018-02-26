package com.santra.sanchita.portfolioapp.data.network;

import android.support.annotation.NonNull;

import com.santra.sanchita.portfolioapp.data.DataManager;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by sanchita on 6/12/17.
 */

public class AddHeadersInterceptor implements Interceptor {

    @Inject
    DataManager dataManager;

    public AddHeadersInterceptor() {
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        if(dataManager.getAuthCode() != null) {
            builder.addHeader("Authorization", "Bearer " + dataManager.getAuthCode());
        }
        builder.method(chain.request().method(), chain.request().body());
        return chain.proceed(builder.build());
    }
}
