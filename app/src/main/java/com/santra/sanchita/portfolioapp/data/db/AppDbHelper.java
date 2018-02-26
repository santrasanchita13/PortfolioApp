package com.santra.sanchita.portfolioapp.data.db;

import com.santra.sanchita.portfolioapp.data.db.model.DaoMaster;
import com.santra.sanchita.portfolioapp.data.db.model.DaoSession;
import com.santra.sanchita.portfolioapp.data.db.model.DesignItem;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by sanchita on 21/11/17.
 */

@Singleton
public class AppDbHelper implements DbHelper {

    private final DaoSession daoSession;

    @Inject
    public AppDbHelper(DbOpenHelper dbOpenHelper) {
        daoSession = new DaoMaster(dbOpenHelper.getWritableDb()).newSession();
    }

    @Override
    public Observable<Long> insertDesignItem(final DesignItem designItem) {
        return Observable.fromCallable(() -> daoSession.getDesignItemDao().insert(designItem));
    }

    @Override
    public Observable<Boolean> insertDesignItems(final List<DesignItem> designItems) {
        return Observable.fromCallable(() -> {
            daoSession.getDesignItemDao().insertInTx(designItems);
            return true;
        });
    }

    @Override
    public Observable<List<DesignItem>> getAllDesignItems() {
        return Observable.fromCallable(() -> daoSession.getDesignItemDao().loadAll());
    }

    @Override
    public Observable<Boolean> isDesignItemEmpty() {
        return Observable.fromCallable(() -> !(daoSession.getDesignItemDao().count() > 0));
    }

    @Override
    public Observable<DesignItem> getDesignItemById(Long id) {
        return Observable.fromCallable(() -> daoSession.getDesignItemDao().load(id));
    }

    @Override
    public Observable<Long> getDesignItemsCount() {
        return Observable.fromCallable(() -> daoSession.getDesignItemDao().count());
    }
}
