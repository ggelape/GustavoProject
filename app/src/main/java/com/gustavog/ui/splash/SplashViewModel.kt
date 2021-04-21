package com.gustavog.ui.splash

import com.gustavog.model.repository.Repository
import com.gustavog.model.storage.DataStorage
import com.gustavog.system.common.viewmodel.BaseViewModel

class SplashViewModel(
    dataStorage: DataStorage,
    repository: Repository
) : BaseViewModel<SplashViewModel.SplashState>() {

    enum class SplashState : BaseState {
        PASSWORD_PROTECTED,
        NOT_PASSWORD_PROTECTED
    }

    init {
        repository.configure()
        if (dataStorage.getPin() != null) {
            setState(SplashState.PASSWORD_PROTECTED)
        } else {
            setState(SplashState.NOT_PASSWORD_PROTECTED)
        }

    }

}
