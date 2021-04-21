package com.gustavog.system.common.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeoutException

abstract class BaseViewModel<S : BaseViewModel.BaseState> : ViewModel() {

    enum class Error {
        TIMEOUT,
        UNKNOWN
    }

    private val errorState = MutableLiveData<Error>()

    interface BaseState

    protected val pool: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        pool.dispose()
    }

    private val viewModelstate: MutableLiveData<S> = MutableLiveData()

    protected fun setState(state: S) {
        viewModelstate.value = state
    }

    fun setErrorState(error: Throwable) {
        errorState.value = when (error) {
            is TimeoutException -> Error.TIMEOUT
            else -> Error.UNKNOWN
        }
    }

    fun getState(): LiveData<S> = viewModelstate
    fun getError(): LiveData<Error> = errorState
}
