package com.santra.sanchita.portfolioapp.data;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.$Gson$Types;
import com.santra.sanchita.portfolioapp.R;
import com.santra.sanchita.portfolioapp.data.db.DbHelper;
import com.santra.sanchita.portfolioapp.data.db.model.DesignItem;
import com.santra.sanchita.portfolioapp.data.network.ApiHelper;
import com.santra.sanchita.portfolioapp.data.prefs.PreferenceHelper;
import com.santra.sanchita.portfolioapp.di.ApplicationContext;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * Created by sanchita on 21/11/17.
 */

@Singleton
public class AppDataManager implements DataManager {

    private final Context context;
    private final DbHelper dbHelper;
    private final PreferenceHelper preferenceHelper;
    private final ApiHelper apiHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context context, DbHelper dbHelper, PreferenceHelper preferenceHelper, ApiHelper apiHelper) {
        this.context = context;
        this.dbHelper = dbHelper;
        this.preferenceHelper = preferenceHelper;
        this.apiHelper = apiHelper;
    }

    @Override
    public String getAuthCode() {
        return preferenceHelper.getAuthCode();
    }

    @Override
    public void setAuthCode(String authCode) {
        preferenceHelper.setAuthCode(authCode);
    }

    @Override
    public Retrofit getRetrofit() {
        return apiHelper.getRetrofit();
    }

    @Override
    public Observable<Boolean> seedDesignItems() {
        GsonBuilder gsonBuilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        final Gson gson = gsonBuilder.create();

        return dbHelper.isDesignItemEmpty()
                .concatMap(isEmpty -> {
                    if(isEmpty) {
                        Type type = $Gson$Types.newParameterizedTypeWithOwner(null, List.class, DesignItem.class);
                        /*List<DesignItem> designItemList = gson.fromJson(CommonUtils.loadJSONFromAsset(context, Constants.SEED_DESIGN_LIST),
                                type);*/
                        List<DesignItem> designItemList = new ArrayList<>();
                        designItemList.add(new DesignItem(0L, null, "Who am I?",
                                R.drawable.introduction + "", R.string.introduction_card_line + "", false));
                        designItemList.add(new DesignItem(1L, null, "My skills",
                                R.drawable.skills_bg + "", R.string.skills_card_line + "", true));
                        designItemList.add(new DesignItem(2L, null, "My interests",
                                R.drawable.interest_bg + "", R.string.interests_card_line + "", false));
                        designItemList.add(new DesignItem(3L, null, "Contact me",
                                R.drawable.contact_bg + "", R.string.contact_card_line + "", false));
                        return insertDesignItems(designItemList);
                    }
                    return Observable.just(false);
                });
    }

    @Override
    public Observable<Long> insertDesignItem(DesignItem designItem) {
        return dbHelper.insertDesignItem(designItem);
    }

    @Override
    public Observable<Boolean> insertDesignItems(List<DesignItem> designItems) {
        return dbHelper.insertDesignItems(designItems);
    }

    @Override
    public Observable<List<DesignItem>> getAllDesignItems() {
        return dbHelper.getAllDesignItems();
    }

    @Override
    public Observable<Boolean> isDesignItemEmpty() {
        return dbHelper.isDesignItemEmpty();
    }

    @Override
    public Observable<DesignItem> getDesignItemById(Long id) {
        return dbHelper.getDesignItemById(id);
    }

    @Override
    public Observable<Long> getDesignItemsCount() {
        return dbHelper.getDesignItemsCount();
    }

    @Override
    public Observable<Boolean> sendEmail(GoogleSignInAccount account, String message) {
        return apiHelper.sendEmail(account, message);
    }

    @Override
    public Observable<String> sendEmailTest(GoogleSignInAccount account, String message) {
        return apiHelper.sendEmailTest(account, message);
    }
}
