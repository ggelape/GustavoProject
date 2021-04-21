package com.gustavog.model.api

import com.gustavog.model.domain.*
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ConsumerApiClientImpl : ConsumerApiClient {

    private lateinit var service: ConsumerApiService

    override fun configure() {

        val httpClient = OkHttpClient.Builder()

        val builder = Retrofit.Builder()
            .baseUrl("https://api.tvmaze.com")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        httpClient.addInterceptor(logging)
        builder.client(httpClient.build())

        val retrofitClient: Retrofit = builder.build()
        service = retrofitClient.create(ConsumerApiService::class.java)
    }

    override fun getShows(page: Int?): Single<SeriesResponse> =
        makeRequest(service.getShows(page))

    override fun searchShows(query: String): Single<SearchResponse> =
        makeRequest(service.searchShows(query))

    override fun getShowDetails(id: String): Single<ShowDetailsResponse> =
        makeRequest(service.getShowDetails(id))

    override fun searchPerson(query: String): Single<PersonSearchResponse> =
        makeRequest(service.searchPerson(query))

    override fun getPersonDetails(id: String): Single<PersonDetailsResponse> =
        makeRequest(service.getPersonDetails(id))

    override fun getPersonShowDetails(id: String): Single<PersonShowDetails> =
        makeRequest(service.getPersonShowDetails(id))

    private fun <T> makeRequest(request: Single<T>) =
        request
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

}
