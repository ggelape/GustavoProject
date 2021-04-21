package com.gustavog.model.domain


import com.google.gson.annotations.SerializedName

data class ShowDetailsResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("language")
    val language: String,
    @SerializedName("genres")
    val genres: List<String>,
    @SerializedName("status")
    val status: String,
    @SerializedName("runtime")
    val runtime: Int,
    @SerializedName("premiered")
    val premiered: String,
    @SerializedName("officialSite")
    val officialSite: String,
    @SerializedName("schedule")
    val schedule: Schedule,
    @SerializedName("rating")
    val rating: Rating,
    @SerializedName("weight")
    val weight: Int,
    @SerializedName("network")
    val network: Network,
    @SerializedName("webChannel")
    val webChannel: Any,
    @SerializedName("dvdCountry")
    val dvdCountry: Any,
    @SerializedName("externals")
    val externals: Externals,
    @SerializedName("image")
    val image: Image,
    @SerializedName("summary")
    val summary: String,
    @SerializedName("updated")
    val updated: Int,
    @SerializedName("_links")
    val links: Links,
    @SerializedName("_embedded")
    val embedded: Embedded
) {
    data class Schedule(
        @SerializedName("time")
        val time: String,
        @SerializedName("days")
        val days: List<String>
    )

    data class Rating(
        @SerializedName("average")
        val average: Double
    )

    data class Network(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("country")
        val country: Country
    ) {
        data class Country(
            @SerializedName("name")
            val name: String,
            @SerializedName("code")
            val code: String,
            @SerializedName("timezone")
            val timezone: String
        )
    }

    data class Externals(
        @SerializedName("tvrage")
        val tvrage: Int,
        @SerializedName("thetvdb")
        val thetvdb: Int,
        @SerializedName("imdb")
        val imdb: String
    )

    data class Image(
        @SerializedName("medium")
        val medium: String,
        @SerializedName("original")
        val original: String
    )

    data class Links(
        @SerializedName("self")
        val self: Self,
        @SerializedName("previousepisode")
        val previousepisode: Previousepisode
    ) {
        data class Self(
            @SerializedName("href")
            val href: String
        )

        data class Previousepisode(
            @SerializedName("href")
            val href: String
        )
    }

    data class Embedded(
        @SerializedName("episodes")
        val episodes: List<Episode>
    ) {
        data class Episode(
            @SerializedName("id")
            val id: Int,
            @SerializedName("url")
            val url: String,
            @SerializedName("name")
            val name: String,
            @SerializedName("season")
            val season: Int,
            @SerializedName("number")
            val number: Int,
            @SerializedName("type")
            val type: String,
            @SerializedName("airdate")
            val airdate: String,
            @SerializedName("airtime")
            val airtime: String,
            @SerializedName("airstamp")
            val airstamp: String,
            @SerializedName("runtime")
            val runtime: Int,
            @SerializedName("image")
            val image: Image,
            @SerializedName("summary")
            val summary: String,
            @SerializedName("_links")
            val links: Links
        ) {
            data class Image(
                @SerializedName("medium")
                val medium: String,
                @SerializedName("original")
                val original: String
            )

            data class Links(
                @SerializedName("self")
                val self: Self
            ) {
                data class Self(
                    @SerializedName("href")
                    val href: String
                )
            }
        }
    }
}
