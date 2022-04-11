package com.gb.advanced2.externals.ui.navigation

class NavigatorHolder {
    private var navigator: Navigator? = null

    fun setNavigator(n: Navigator) { navigator = n }
    fun getNavigator() = navigator!!
    fun removeNavigator() { navigator = null }
}