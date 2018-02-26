package com.santra.sanchita.portfolioapp.data.network;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.santra.sanchita.portfolioapp.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sanchita on 6/12/17.
 */

public class RetrofitInstance {

    private RetrofitInstance() {
    }

    public static Retrofit getInstance(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(RestClient.getOkHttpClientLocal(context))
                .build();
        return retrofit;
    }

}
