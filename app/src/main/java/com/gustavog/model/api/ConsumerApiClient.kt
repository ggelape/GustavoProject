package com.gustavog.model.api

import com.gustavog.model.domain.*
import io.reactivex.Single
import retrofit2.http.Path

interface ConsumerApiClient : Api {

    fun getShows(page: Int?): Single<SeriesResponse>
    fun searchShows(query: String): Single<SearchResponse>
    fun searchPerson(query: String): Single<PersonSearchResponse>
    fun getShowDetails(id: String): Single<ShowDetailsResponse>
    fun getPersonDetails(id: String): Single<PersonDetailsResponse>
    fun getPersonShowDetails(id: String): Single<PersonShowDetails>
}
