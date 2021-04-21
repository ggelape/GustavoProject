package com.gustavog.ui.navigation

import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.FragmentActivity
import com.gustavog.ui.dashboard.DashboardActivity
import com.gustavog.ui.episodedetails.EpisodeDetailsActivity
import com.gustavog.ui.favoritelist.FavoriteListActivity
import com.gustavog.ui.onboarding.PasswordLockActivity
import com.gustavog.ui.passwordprotect.PasswordProtectActivity
import com.gustavog.ui.persondetails.PersonDetailsActivity
import com.gustavog.ui.searchable.SearchableActivity
import com.gustavog.ui.seriesdetails.SeriesDetailsActivity
import com.gustavog.ui.splash.SplashActivity

class NavigationImpl : Navigation {

    override fun restartApp(activity: FragmentActivity) {
        startActivity(activity, SplashActivity::class.java, true)
    }

    override fun goToPasswordLock(activity: FragmentActivity, restart: Boolean, withDelayOf: Long) {
        startActivity(activity, PasswordLockActivity::class.java, restart, withDelayOf = withDelayOf)
    }

    override fun goToSearch(activity: FragmentActivity, restart: Boolean, withDelayOf: Long) {
        startActivity(activity, SearchableActivity::class.java, restart)
    }

    override fun goToMain(activity: FragmentActivity, restart: Boolean, withDelayOf: Long) {
        startActivity(activity, DashboardActivity::class.java, restart, withDelayOf = withDelayOf)
    }

    override fun goToSeriesDetails(activity: FragmentActivity, restart: Boolean, extraName: String, extra: String) {
        startActivity(activity, SeriesDetailsActivity::class.java, restart, extraName, extra)
    }

    override fun goToPersonDetails(activity: FragmentActivity, restart: Boolean, extraName: String, extra: String) {
        startActivity(activity, PersonDetailsActivity::class.java, restart, extraName, extra)
    }

    override fun goToEpisodeDetails(activity: FragmentActivity, restart: Boolean) {
        startActivity(activity, EpisodeDetailsActivity::class.java, restart)
    }

    override fun goToFavorites(activity: FragmentActivity, restart: Boolean) {
        startActivity(activity, FavoriteListActivity::class.java, restart)
    }

    override fun goToPasswordProtect(activity: FragmentActivity, restart: Boolean) {
        startActivity(activity, PasswordProtectActivity::class.java, restart)
    }

    private fun <T> startActivity(
        currentActivity: FragmentActivity,
        destination: Class<T>,
        restart: Boolean,
        extraName: String? = null,
        extra: String? = null,
        withDelayOf: Long = 0
    ) {

        val intent = Intent(currentActivity, destination)
        if (extraName != null) {
            intent.putExtra(extraName, extra)
        }

        if (withDelayOf > 0) {

            Handler(Looper.getMainLooper()).postDelayed({

                currentActivity.startActivity(intent)
                if (restart) {
                    currentActivity.finishAffinity()
                }

            }, withDelayOf)

        } else {
            currentActivity.startActivity(intent)
            if (restart) {
                currentActivity.finishAffinity()
            }
        }


    }
}
