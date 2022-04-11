package com.gb.advanced2.app

import com.gb.advanced2.adapters.MainViewModel
import com.gb.advanced2.externals.os.DispatcherProvider
import com.gb.advanced2.externals.repo.articles.RemoteRepository
import com.gb.advanced2.externals.repo.history.LocalRepository
import com.gb.advanced2.externals.ui.navigation.NavigatorHolder
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val koinModule = module {
    single<Contract.ArticlesModel> { RemoteRepository() }
    single<Contract.HistoryModel> { LocalRepository(context = androidContext()) }
    single { DispatcherProvider() }
    single<Contract.ViewModel> {
        MainViewModel(
            articlesModel = get(),
            historyModel = get(),
            dispatcherProvider = get(),
        )
    }
    single { NavigatorHolder() }
}