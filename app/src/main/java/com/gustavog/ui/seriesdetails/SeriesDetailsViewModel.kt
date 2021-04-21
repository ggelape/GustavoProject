package com.gustavog.ui.seriesdetails

import androidx.lifecycle.MutableLiveData
import com.gustavog.model.domain.ShowDetailsResponse
import com.gustavog.model.repository.Repository
import com.gustavog.model.storage.DataStorage
import com.gustavog.system.common.viewmodel.BaseViewModel
import com.gustavog.system.extensions.disposedBy

class SeriesDetailsViewModel(
    private val repository: Repository,
    private val dataStorage: DataStorage
) : BaseViewModel<SeriesDetailsViewModel.State>() {

    enum class State: BaseState {
        FAVORITE,
        NO_FAVORITE
    }

    private val showDetailsLiveData = MutableLiveData<ShowDetailsResponse>()
    private var currentFavorite: List<ShowDetailsResponse>? = null

    fun getShowDetails() = showDetailsLiveData


    fun getDetails(id: String) {
        repository.getShowDetails(id)
            .doOnSuccess {
                showDetailsLiveData.value = it
            }
            .doOnError {

            }
            .disposedBy(pool)

        currentFavorite = dataStorage.getFavorites()?.filter { it.id.toString() == id }
        currentFavorite?.let { list ->
            if (list.isNotEmpty()) {
                setState(State.FAVORITE)
            }
        }
    }

    fun saveSelectedEpisode(episode: ShowDetailsResponse.Embedded.Episode) {
        dataStorage.saveSelectedEpisode(episode)
    }

    fun saveFavorite() {
        currentFavorite?.let { list ->
            if (list.isNotEmpty()) {
                dataStorage.removeFavorite(list[0])
                currentFavorite = null
                setState(State.NO_FAVORITE)
                return
            }
        }

        showDetailsLiveData.value?.let { response ->
            dataStorage.saveFavorite(response)
            currentFavorite = dataStorage.getFavorites()?.filter { it.id == response.id }
            setState(State.FAVORITE)
        }
    }
}
