package com.gustavog.ui.dashboard

import androidx.lifecycle.MutableLiveData
import com.gustavog.model.domain.SeriesResponse
import com.gustavog.model.repository.Repository
import com.gustavog.model.storage.DataStorage
import com.gustavog.system.common.viewmodel.BaseViewModel
import com.gustavog.system.extensions.disposedBy

class DashboardViewModel(
    private val repository: Repository,
    private val dataStorage: DataStorage
) :
    BaseViewModel<DashboardViewModel.State>() {

    private var nextPage: Int? = null

    enum class State : BaseState {
        REMOVE_LOCK,
        ADD_LOCK
    }

    init {
        getShows()
    }

    private val showsLiveData = MutableLiveData<SeriesResponse>()

    fun getShowsLiveData() = showsLiveData

    fun getShows() {
        repository.getShows(nextPage)
            .doOnSuccess {
                nextPage = if (nextPage == null) 1 else nextPage!! + 1
                showsLiveData.value = it
            }
            .doOnError {
                setErrorState(it)
            }
            .disposedBy(pool)
    }

    fun lockOrUnlock() {
        dataStorage.getPin()?.let {
            setState(State.REMOVE_LOCK)
            return
        }
        setState(State.ADD_LOCK)
    }

    fun removePassword() {
        dataStorage.savePin(null)
    }

}
