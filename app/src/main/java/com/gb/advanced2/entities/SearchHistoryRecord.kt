package com.gb.advanced2.entities

data class SearchHistoryRecord(
    val searchQuery: String,
    val resultsCount: Int,
)

typealias SearchHistoryRecords = ArrayList<SearchHistoryRecord>