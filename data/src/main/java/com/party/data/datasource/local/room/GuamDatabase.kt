package com.party.data.datasource.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.party.common.Constants.GUAM_DATABASE_NAME
import com.party.data.datasource.local.room.dao.KeywordDao
import com.party.data.datasource.local.room.entity.KeywordEntity

@Database(
    entities = [KeywordEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GuamDatabase: RoomDatabase() {
    companion object {
        const val DB_NAME = GUAM_DATABASE_NAME
    }

    abstract fun keywordDAO() : KeywordDao
}