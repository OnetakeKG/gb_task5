package com.gb.advanced2.externals.os

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DispatcherProvider {
    fun io() : CoroutineDispatcher = Dispatchers.IO
    fun ui() : CoroutineDispatcher = Dispatchers.Main
}