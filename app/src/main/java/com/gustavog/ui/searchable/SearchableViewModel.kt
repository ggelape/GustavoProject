package com.gustavog.ui.searchable

import androidx.lifecycle.MutableLiveData
import com.gustavog.model.domain.PersonSearchResponse
import com.gustavog.model.domain.SearchResponse
import com.gustavog.model.repository.Repository
import com.gustavog.system.common.viewmodel.BaseViewModel
import com.gustavog.system.extensions.disposedBy

class SearchableViewModel(private val repository: Repository) : BaseViewModel<SearchableViewModel.State>() {

    enum class State : BaseState

    private val searchResultLiveData = MutableLiveData<SearchResponse>()
    private val personSearchResultLiveData = MutableLiveData<PersonSearchResponse>()

    fun getSearchResults() = searchResultLiveData
    fun getPersonSearchResults() = personSearchResultLiveData

    fun searchShow(query: String) {
        repository.searchShows(query)
            .doOnSuccess {
                searchResultLiveData.value = it
            }
            .doOnError {

            }
            .disposedBy(pool)
    }

    fun searchPerson(query: String) {
        repository.searchPerson(query)
            .doOnSuccess {
                personSearchResultLiveData.value = it
            }
            .doOnError {

            }
            .disposedBy(pool)
    }

}
