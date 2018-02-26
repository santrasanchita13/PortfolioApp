package com.santra.sanchita.portfolioapp.data.db;

import com.santra.sanchita.portfolioapp.data.db.model.DesignItem;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by sanchita on 20/11/17.
 */

public interface DbHelper {

    Observable<Long> insertDesignItem(final DesignItem designItem);

    Observable<Boolean> insertDesignItems(final List<DesignItem> designItems);

    Observable<List<DesignItem>> getAllDesignItems();

    Observable<Boolean> isDesignItemEmpty();

    Observable<DesignItem> getDesignItemById(Long id);

    Observable<Long> getDesignItemsCount();

}
