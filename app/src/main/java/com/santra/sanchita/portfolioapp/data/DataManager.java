package com.santra.sanchita.portfolioapp.data;

import com.santra.sanchita.portfolioapp.data.db.DbHelper;
import com.santra.sanchita.portfolioapp.data.network.ApiHelper;
import com.santra.sanchita.portfolioapp.data.prefs.PreferenceHelper;

import io.reactivex.Observable;

/**
 * Created by sanchita on 20/11/17.
 */

public interface DataManager extends DbHelper, ApiHelper, PreferenceHelper {

    Observable<Boolean> seedDesignItems();
}
