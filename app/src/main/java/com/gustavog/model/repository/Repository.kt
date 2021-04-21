package com.gustavog.model.repository

import com.gustavog.model.domain.*
import io.reactivex.Single

interface Repository {

    fun configure()
    fun getShows(page: Int?): Single<SeriesResponse>
    fun searchShows(query: String): Single<SearchResponse>
    fun searchPerson(query: String): Single<PersonSearchResponse>
    fun getShowDetails(id: String): Single<ShowDetailsResponse>
    fun getPersonDetails(id: String): Single<PersonDetailsResponse>
    fun getPersonShowDetails(id: String): Single<PersonShowDetails>

}
