package com.gustavog.system.injection

import com.gustavog.ui.dashboard.DashboardViewModel
import com.gustavog.ui.episodedetails.EpisodeDetailsViewModel
import com.gustavog.ui.favoritelist.FavoriteListViewModel
import com.gustavog.ui.onboarding.PasswordLockViewModel
import com.gustavog.ui.passwordprotect.PasswordProtectViewModel
import com.gustavog.ui.persondetails.PersonDetailsViewModel
import com.gustavog.ui.searchable.SearchableViewModel
import com.gustavog.ui.seriesdetails.SeriesDetailsViewModel
import com.gustavog.ui.splash.SplashViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModules = module {
    viewModel { SplashViewModel(get(), get()) }
    viewModel { SearchableViewModel(get()) }
    viewModel { DashboardViewModel(get(), get()) }
    viewModel { PasswordLockViewModel(get()) }
    viewModel { SeriesDetailsViewModel(get(), get()) }
    viewModel { PersonDetailsViewModel(get()) }
    viewModel { EpisodeDetailsViewModel(get()) }
    viewModel { FavoriteListViewModel(get()) }
    viewModel { PasswordProtectViewModel(get()) }
}
