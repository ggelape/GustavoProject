package com.gustavog.model.api

import com.gustavog.model.domain.*
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ConsumerApiService {

    @GET("/shows")
    fun getShows(@Query("page") page: Int?): Single<SeriesResponse>

    @GET("/search/shows")
    fun searchShows(@Query("q") query: String): Single<SearchResponse>

    @GET("/search/people")
    fun searchPerson(@Query("q") query: String): Single<PersonSearchResponse>

    @GET("/shows/{id}?embed=episodes")
    fun getShowDetails(@Path("id") id: String): Single<ShowDetailsResponse>

    @GET("/people/{id}")
    fun getPersonDetails(@Path("id") id: String): Single<PersonDetailsResponse>

    @GET("/people/{id}/castcredits?embed=show")
    fun getPersonShowDetails(@Path("id") id: String): Single<PersonShowDetails>
}
