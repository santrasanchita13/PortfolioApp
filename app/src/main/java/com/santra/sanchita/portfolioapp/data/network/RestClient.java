package com.santra.sanchita.portfolioapp.data.network;

import android.content.Context;
import android.os.Build;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;

/**
 * Created by sanchita on 6/12/17.
 */

public class RestClient {
    public static OkHttpClient getOkHttpClient(Context context) {

        try {
            ProviderInstaller.installIfNeeded(context);

            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[0];
                        }
                    }
            };

            // Install the all-trusting trust manager
            SSLContext sslContext;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                sslContext = SSLContext.getInstance("TLS");
            }
            else {
                sslContext = SSLContext.getInstance("TLSv1.2");
            }
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
            okHttpClientBuilder.readTimeout(15000L, TimeUnit.MILLISECONDS);
            okHttpClientBuilder.connectTimeout(15000L, TimeUnit.MILLISECONDS);
            okHttpClientBuilder.sslSocketFactory(sslSocketFactory, new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            });
            okHttpClientBuilder.hostnameVerifier((hostname, session) -> true);

            okHttpClientBuilder.connectionSpecs(Collections.singletonList(ConnectionSpec.MODERN_TLS));

            okHttpClientBuilder.followRedirects(true);
            okHttpClientBuilder.followSslRedirects(true);

            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            //okHttpClientBuilder.cookieJar(new JavaNetCookieJar(cookieManager));

            okHttpClientBuilder.addInterceptor(new AddHeadersInterceptor())
                    .addInterceptor(new ReceivedHeadersInterceptor());

            OkHttpClient okHttpClient = okHttpClientBuilder.build();

            return okHttpClient;

        } catch (GooglePlayServicesNotAvailableException | NoSuchAlgorithmException | KeyManagementException | GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static OkHttpClient getOkHttpClientLocal(Context context) {

        try {
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
            okHttpClientBuilder.hostnameVerifier((hostname, session) -> true);

            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            //okHttpClientBuilder.cookieJar(new JavaNetCookieJar(cookieManager));
            okHttpClientBuilder.addInterceptor(new AddHeadersInterceptor())
                    .addInterceptor(new ReceivedHeadersInterceptor());

            OkHttpClient okHttpClient = okHttpClientBuilder.build();

            return okHttpClient;

        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }
}
