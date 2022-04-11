package com.gb.advanced2.externals.repo.history

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.gb.advanced2.app.Contract
import com.gb.advanced2.entities.SearchHistoryRecord
import com.gb.advanced2.entities.SearchHistoryRecords
import com.gb.advanced2.externals.repo.history.db.AppDatabase
import com.gb.advanced2.externals.repo.history.db.entity.DbSearchHistoryRecord
import java.text.SimpleDateFormat
import java.util.*

class LocalRepository(context: Context) : Contract.HistoryModel {
    override suspend fun loadHistory(): SearchHistoryRecords {
        val list = db.historyDao().getHistory(historyRecentLimit).map {
            SearchHistoryRecord(
                it.searchQuery,
                it.resultsCount,
            )
        }
        Log.d("===", "from room: $list")
        return SearchHistoryRecords(list)
    }

    private val timeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS", Locale.getDefault())

    override suspend fun saveHistoryRecord(record: SearchHistoryRecord) {
        val timestamp = timeFormat.format(Calendar.getInstance().time)
        Log.d("===", "to room: ts=$timestamp record=$record")
        db.historyDao().putHistoryRecord(
            DbSearchHistoryRecord(
                searchQuery = record.searchQuery,
                resultsCount = record.resultsCount,
                timestamp = timestamp,
            )
        )
    }

    private val db = Room.databaseBuilder(context, AppDatabase::class.java, "db2").build()
    private val historyRecentLimit = 10
}