package com.santra.sanchita.portfolioapp.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.santra.sanchita.portfolioapp.data.db.model.DaoMaster;
import com.santra.sanchita.portfolioapp.di.ApplicationContext;
import com.santra.sanchita.portfolioapp.di.DatabaseInfo;
import com.santra.sanchita.portfolioapp.utils.AppLogger;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by sanchita on 9/12/17.
 */

@Singleton
public class DbOpenHelper extends DaoMaster.OpenHelper {

    @Inject
    public DbOpenHelper(@ApplicationContext Context context, @DatabaseInfo String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        AppLogger.d("DEBUG", "DB_OLD_VERSION : " + oldVersion + ", DB_NEW_VERSION : " + newVersion);
        switch (oldVersion) {
            case 1:
            case 2:
                //db.execSQL("ALTER TABLE " + UserDao.TABLENAME + " ADD COLUMN "
                // + UserDao.Properties.Name.columnName + " TEXT DEFAULT 'DEFAULT_VAL'");
        }
    }
}
