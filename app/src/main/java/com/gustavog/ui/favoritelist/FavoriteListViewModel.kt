package com.gustavog.ui.favoritelist

import androidx.lifecycle.MutableLiveData
import com.gustavog.model.domain.ShowDetailsResponse
import com.gustavog.model.storage.DataStorage
import com.gustavog.system.common.viewmodel.BaseViewModel

class FavoriteListViewModel(private val dataStorage: DataStorage) : BaseViewModel<FavoriteListViewModel.State>() {
    enum class State: BaseState

    private val favoritesLiveData = MutableLiveData<ArrayList<ShowDetailsResponse>>()

    fun getFavorites() = favoritesLiveData

    fun retrieveFavorites() {
        favoritesLiveData.value = ArrayList(dataStorage.getFavorites()?.sortedBy { it.name })
    }

    init {
        retrieveFavorites()
    }
}
