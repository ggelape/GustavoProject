package com.gustavog.ui.persondetails

import androidx.lifecycle.MutableLiveData
import com.gustavog.model.domain.PersonDetailsResponse
import com.gustavog.model.domain.PersonShowDetails
import com.gustavog.model.repository.Repository
import com.gustavog.system.common.viewmodel.BaseViewModel
import com.gustavog.system.extensions.disposedBy

class PersonDetailsViewModel(private val repository: Repository) : BaseViewModel<PersonDetailsViewModel.State>() {
    enum class State: BaseState

    private val personDetailsLiveData = MutableLiveData<PersonDetailsResponse>()
    private val personShowDetailsLiveData = MutableLiveData<PersonShowDetails>()

    fun getPersonDetails() = personDetailsLiveData
    fun getPersonShowDetails() = personShowDetailsLiveData

    fun fetchPersonDetails(id: String) {
        repository.getPersonDetails(id)
            .zipWith(repository.getPersonShowDetails(id), { details, showDetails ->
                Pair(details, showDetails)
            })
            .doOnSuccess {
                personDetailsLiveData.value = it.first
                personShowDetailsLiveData.value = it.second
            }
            .doOnError {

            }
            .disposedBy(pool)
    }
}
