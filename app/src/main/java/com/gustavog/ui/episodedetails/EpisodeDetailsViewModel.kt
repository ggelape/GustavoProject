package com.gustavog.ui.episodedetails

import androidx.lifecycle.MutableLiveData
import com.gustavog.model.domain.ShowDetailsResponse
import com.gustavog.model.storage.DataStorage
import com.gustavog.system.common.viewmodel.BaseViewModel

class EpisodeDetailsViewModel(val dataStorage: DataStorage) : BaseViewModel<EpisodeDetailsViewModel.State>() {
    enum class State: BaseState

    private val episodeLiveData = MutableLiveData<ShowDetailsResponse.Embedded.Episode>()

    fun getEpisode() = episodeLiveData

    init {
        episodeLiveData.value = dataStorage.getSelectedEpisode()
    }
}
