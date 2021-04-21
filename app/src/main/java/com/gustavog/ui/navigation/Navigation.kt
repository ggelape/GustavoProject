package com.gustavog.ui.navigation

import androidx.fragment.app.FragmentActivity

interface Navigation {

    fun restartApp(activity: FragmentActivity)
    fun goToPasswordLock(activity: FragmentActivity, restart: Boolean = true, withDelayOf: Long = 0)
    fun goToSearch(activity: FragmentActivity, restart: Boolean = true, withDelayOf: Long = 0)
    fun goToMain(activity: FragmentActivity, restart: Boolean = true, withDelayOf: Long = 0)
    fun goToSeriesDetails(activity: FragmentActivity, restart: Boolean = true, extraName: String, extra: String)
    fun goToPersonDetails(activity: FragmentActivity, restart: Boolean = true, extraName: String, extra: String)
    fun goToEpisodeDetails(activity: FragmentActivity, restart: Boolean)
    fun goToFavorites(activity: FragmentActivity, restart: Boolean)
    fun goToPasswordProtect(activity: FragmentActivity, restart: Boolean)

}
