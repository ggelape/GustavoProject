package com.gustavog.ui.splash

import com.gustavog.R
import com.gustavog.system.common.activity.BaseActivity
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity<SplashViewModel, SplashViewModel.SplashState>() {

    companion object {
        const val SPLASH_SCREEN_TIME = 2000L
    }

    override val model by viewModel<SplashViewModel>()

    override fun layoutResource(): Int = R.layout.activity_splash

    override fun setupViews() = Unit

    override fun setupBindings() = Unit

    override fun onStateChanged(state: SplashViewModel.SplashState) {

        when (state) {
            SplashViewModel.SplashState.PASSWORD_PROTECTED -> {
                navigation.goToPasswordLock(this, true, SPLASH_SCREEN_TIME)
            }

            SplashViewModel.SplashState.NOT_PASSWORD_PROTECTED -> {
                navigation.goToMain(this, true, SPLASH_SCREEN_TIME)
            }
        }

    }
}
