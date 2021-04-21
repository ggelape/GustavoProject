package com.gustavog.ui.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gustavog.model.storage.DataStorage
import com.gustavog.system.common.viewmodel.BaseViewModel

class PasswordLockViewModel(
    private val dataStorage: DataStorage
) : BaseViewModel<PasswordLockViewModel.State>() {

    enum class State : BaseState {
        PASSWORD_CORRECT,
        PASSWORD_INCORRECT
    }

    private val passwordLiveData = MutableLiveData<String>()

    fun getPassword(): LiveData<String> = passwordLiveData

    fun setPassword(newValue: String) {
        passwordLiveData.value = newValue
    }

    fun unlock() {
        passwordLiveData.value?.let {
            if (it == dataStorage.getPin()) {
                setState(State.PASSWORD_CORRECT)
            } else {
                setState(State.PASSWORD_INCORRECT)
            }
        }
    }

}
