package com.talentomobile.assignment.dbdata

import com.talentomobile.assignment.dbdata.db.PlaceDB
import com.talentomobile.assignment.dbdata.db.dao.PlaceDBDao

/**
 * Created by pavel on 10/2/18.
 */

class DataBaseProvider{

    companion object {
        fun insertPlace(place: PlaceDB) {
            DBManager.getPlaceDao().insert(place)
        }


        fun getAllPlaces(): List<PlaceDB> {

            return DBManager.getPlaceDao().loadAll()
        }

        fun searchPlace(name: String): Boolean {

            var check = false
            val users = DBManager.getPlaceDao().queryBuilder()
                    .where(PlaceDBDao.Properties.Name.eq(name))
                    .list()
            check = users.size > 0

            return check
        }

        fun searchSpecific(name: String): PlaceDB? {

            val users = DBManager.getPlaceDao().queryBuilder()
                    .where(PlaceDBDao.Properties.Name.eq(name))
                    .list()

            return if (users.size > 0) users[0] as PlaceDB else null
        }
    }
}