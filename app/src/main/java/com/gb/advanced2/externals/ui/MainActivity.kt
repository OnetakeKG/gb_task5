package com.gb.advanced2.externals.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gb.advanced2.R
import com.gb.advanced2.externals.ui.history.HistoryFragment
import com.gb.advanced2.externals.ui.navigation.Navigator
import com.gb.advanced2.externals.ui.navigation.NavigatorHolder
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val navigatorHolder: NavigatorHolder by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(object : Navigator {
            override fun goToHistory() {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, HistoryFragment())
                    .addToBackStack(null)
                    .commit()
            }

            override fun goBack() {
                supportFragmentManager.popBackStack()
            }
        })
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }
}