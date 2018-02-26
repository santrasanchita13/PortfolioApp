package com.santra.sanchita.portfolioapp.data.network;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * Created by sanchita on 21/11/17.
 */

public interface ApiHelper {
    Retrofit getRetrofit();

    Observable<Boolean> sendEmail(GoogleSignInAccount account, String message);

    Observable<String> sendEmailTest(GoogleSignInAccount account, String message);
}
