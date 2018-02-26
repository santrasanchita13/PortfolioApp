package com.santra.sanchita.portfolioapp.data.network;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by sanchita on 6/12/17.
 */

public class ReceivedHeadersInterceptor implements Interceptor {

    private okhttp3.Response originalResponse;

    public ReceivedHeadersInterceptor() {
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        originalResponse = chain.proceed(chain.request());
        return originalResponse;
    }
}
