package com.santra.sanchita.portfolioapp.data.network;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.santra.sanchita.portfolioapp.di.ApplicationContext;
import com.santra.sanchita.portfolioapp.utils.Constants;
import com.sun.mail.smtp.SMTPTransport;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * Created by sanchita on 21/11/17.
 */

@Singleton
public class AppApiHelper implements ApiHelper {

    Context context;

    String accessToken = null;

    @Inject
    public AppApiHelper(@ApplicationContext Context mContext) {
        context = mContext;
    }

    @Override
    public Retrofit getRetrofit() {
        return RetrofitInstance.getInstance(context);
    }

    @Override
    public Observable<Boolean> sendEmail(GoogleSignInAccount account, String message) {
        return Observable.fromCallable(() -> {

            if(account.getAccount() != null && account.getEmail() != null) {

                try {

                    accessToken = getAccessToken();

                    Properties props = new Properties();
                    props.put("mail.smtp.ssl.enable", "true"); // required for Gmail
                    props.put("mail.smtp.auth.mechanisms", "XOAUTH2");
                    Session session = Session.getInstance(props);

                    final URLName unusedUrlName = null;
                    SMTPTransport transport = new SMTPTransport(session, unusedUrlName);
                    transport.connect("smtp.gmail.com", account.getEmail(), accessToken);

                    MimeMessage msg = new MimeMessage(session);
                    msg.setFrom(new InternetAddress(account.getEmail()));
                    InternetAddress[] address = {new InternetAddress(Constants.MY_EMAIL)};
                    msg.setRecipients(Message.RecipientType.TO, address);
                    msg.setSubject("Message from Portfolio App");
                    msg.setSentDate(new Date());
                    msg.setText(message);

                    transport.sendMessage(msg, msg.getAllRecipients());

                    return true;

                } catch (Exception e) {
                    e.printStackTrace();

                    return false;
                }
            }
            return false;
        });
    }

    @Override
    public Observable<String> sendEmailTest(GoogleSignInAccount account, String message) {
        return Observable.fromCallable(() -> {

            if(account.getAccount() != null && account.getEmail() != null) {

                try {

                    accessToken = getAccessToken();

                    Properties props = new Properties();
                    props.put("mail.smtp.ssl.enable", "true"); // required for Gmail
                    props.put("mail.smtp.auth.mechanisms", "XOAUTH2");
                    Session session = Session.getInstance(props);

                    final URLName unusedUrlName = null;
                    SMTPTransport transport = new SMTPTransport(session, unusedUrlName);
                    transport.connect("smtp.gmail.com", account.getEmail(), accessToken);

                    MimeMessage msg = new MimeMessage(session);
                    msg.setFrom(new InternetAddress(account.getEmail()));
                    InternetAddress[] address = {new InternetAddress(Constants.MY_EMAIL)};
                    msg.setRecipients(Message.RecipientType.TO, address);
                    msg.setSubject("Message from Portfolio App");
                    msg.setSentDate(new Date());
                    msg.setText(message);

                    transport.sendMessage(msg, msg.getAllRecipients());

                    return "Access Token :" + accessToken;

                } catch (Exception e) {
                    e.printStackTrace();

                    return "Error1 :" + e.toString();
                }
            }
            return "Null account";
        });
    }

    private String getAccessToken() {

        SharedPreferences sp = context.getSharedPreferences(Constants.SHARED_PREF_AUTH_CODE, Context.MODE_PRIVATE);

        String authCode = sp.getString(Constants.AUTH_CODE, "");

        try {
            // Exchange auth code for access token
            GoogleClientSecrets clientSecrets =
                    GoogleClientSecrets.load(
                            JacksonFactory.getDefaultInstance(), new InputStreamReader(context.getAssets().open(Constants.CLIENT_SECRET_FILE)));

            File file = new File(context.getFilesDir().getAbsolutePath(), "pakhidir");
            file.mkdir();

            GoogleCredential credential = new GoogleCredential.Builder()
                    .setTransport(new NetHttpTransport())
                    .setJsonFactory(JacksonFactory.getDefaultInstance())
                    .setTokenServerEncodedUrl("https://www.googleapis.com/oauth2/v4/token")
                    .setClientSecrets(clientSecrets.getDetails().getClientId(),
                            clientSecrets.getDetails().getClientSecret())
                    .build();

            if(!authCode.isEmpty()) {
                GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                        new NetHttpTransport(),
                        JacksonFactory.getDefaultInstance(),
                        "https://www.googleapis.com/oauth2/v4/token",
                        clientSecrets.getDetails().getClientId(),
                        clientSecrets.getDetails().getClientSecret(),
                        authCode,
                        "")
                        .execute();

                accessToken = tokenResponse.getAccessToken();

                credential.setFromTokenResponse(tokenResponse);

                DataStore<StoredCredential> dataStore = StoredCredential.getDefaultDataStore(new FileDataStoreFactory(file));
                dataStore.set(Constants.STORED_CREDENTIAL, new StoredCredential(credential));

                sp.edit().putString(Constants.AUTH_CODE, "").commit();
            }
            else {
                DataStore<StoredCredential> dataStore = StoredCredential.getDefaultDataStore(new FileDataStoreFactory(file));

                //Boolean refreshed = credential.refreshToken();

                GoogleRefreshTokenRequest refresh = new GoogleRefreshTokenRequest(
                        new NetHttpTransport(),
                        JacksonFactory.getDefaultInstance(),
                        dataStore.get(Constants.STORED_CREDENTIAL).getRefreshToken(),
                        clientSecrets.getDetails().getClientId(),
                        clientSecrets.getDetails().getClientSecret());

                GoogleTokenResponse response = refresh.execute();

                accessToken = response.getAccessToken();

                if(response.getRefreshToken() != null && !response.getRefreshToken().isEmpty()) {
                    credential.setFromTokenResponse(response);

                    dataStore.set(Constants.STORED_CREDENTIAL, new StoredCredential(credential));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

            return e.toString();
        }

        return accessToken;
    }
}
