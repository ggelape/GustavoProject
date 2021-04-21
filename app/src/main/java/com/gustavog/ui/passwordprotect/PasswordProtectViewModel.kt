package com.gustavog.ui.passwordprotect

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gustavog.model.storage.DataStorage
import com.gustavog.system.common.viewmodel.BaseViewModel

class PasswordProtectViewModel(val dataStorage: DataStorage) : BaseViewModel<PasswordProtectViewModel.State>() {
    enum class State: BaseState

    private val passwordLiveData = MutableLiveData<String>()

    fun getPassword(): LiveData<String> = passwordLiveData

    fun setPassword(newValue: String) {
        passwordLiveData.value = newValue
    }

    fun savePin() {
        dataStorage.savePin(passwordLiveData.value)
    }
}
