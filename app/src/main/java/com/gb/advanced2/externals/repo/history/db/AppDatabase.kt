package com.gb.advanced2.externals.repo.history.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gb.advanced2.externals.repo.history.db.dao.DbSearchHistoryDao
import com.gb.advanced2.externals.repo.history.db.entity.DbSearchHistoryRecord

@Database(
    entities = [
        DbSearchHistoryRecord::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): DbSearchHistoryDao
}