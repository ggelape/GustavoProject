package com.gustavog.model.repository

import com.gustavog.model.api.ConsumerApiClient
import com.gustavog.model.domain.*
import com.gustavog.model.storage.DataStorage
import io.reactivex.Single

class RepositoryImpl(private val api: ConsumerApiClient) : Repository {

    override fun configure() {
        api.configure()
    }

    override fun getShows(page: Int?): Single<SeriesResponse> =
        api.getShows(page)

    override fun searchShows(query: String): Single<SearchResponse> =
        api.searchShows(query)

    override fun searchPerson(query: String): Single<PersonSearchResponse> =
        api.searchPerson(query)

    override fun getShowDetails(id: String): Single<ShowDetailsResponse> =
        api.getShowDetails(id)

    override fun getPersonDetails(id: String): Single<PersonDetailsResponse> =
        api.getPersonDetails(id)

    override fun getPersonShowDetails(id: String): Single<PersonShowDetails> =
        api.getPersonShowDetails(id)
}
