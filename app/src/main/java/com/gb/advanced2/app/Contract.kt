package com.gb.advanced2.app

import androidx.lifecycle.LiveData
import com.gb.advanced2.entities.Articles
import com.gb.advanced2.entities.SearchHistoryRecord
import com.gb.advanced2.entities.SearchHistoryRecords

class Contract {

    sealed class SearchScreenState {
        class Empty : SearchScreenState()                               // nothing loaded yet
        class Loading : SearchScreenState()                             // showing progress bar
        data class DataLoaded(val data: Articles) : SearchScreenState()
        data class Error(val error: String) : SearchScreenState()
    }

    sealed class HistoryScreenState {
        class Loading : HistoryScreenState()
        data class HistoryLoaded(val history: SearchHistoryRecords) : HistoryScreenState()
        data class Error(val error: String) : HistoryScreenState()
    }

    interface ViewModel {
        fun getSearchScreenState(): LiveData<SearchScreenState>
        fun getHistoryScreenState(): LiveData<HistoryScreenState>
        fun search(searchString: String)
    }

    interface ArticlesModel {
        suspend fun getArticles(searchString: String): Articles
    }

    interface HistoryModel {
        suspend fun loadHistory(): SearchHistoryRecords
        suspend fun saveHistoryRecord(record: SearchHistoryRecord)
    }
}