package com.talentomobile.assignment.dbdata;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.talentomobile.assignment.dbdata.db.dao.DaoMaster;
import com.talentomobile.assignment.dbdata.db.dao.DaoSession;
import com.talentomobile.assignment.dbdata.db.dao.PlaceDBDao;
import com.talentomobile.assignment.utils.Constants;

/**
 * Created by pavel on 10/2/18.
 */

public class DBManager {

    private static SQLiteDatabase db;

    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    private DBManager() {
    }

    /**
     * Init database
     *
     * @param app
     */
    public static void init(Application app) {


        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(app.getApplicationContext(), Constants.DB_NAME, null);

        db = helper.getWritableDatabase();

        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static PlaceDBDao getPlaceDao() {

        return daoSession.getPlaceDBDao();
    }
}
