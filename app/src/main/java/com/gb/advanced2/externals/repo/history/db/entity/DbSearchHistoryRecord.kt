package com.gb.advanced2.externals.repo.history.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_history")
data class DbSearchHistoryRecord(
    @PrimaryKey @ColumnInfo(name = "search_query") val searchQuery: String,
    @ColumnInfo(name = "results_count") val resultsCount: Int,
    @ColumnInfo(name = "timestamp") val timestamp: String,
)
