package com.gustavog.model.domain


import com.google.gson.annotations.SerializedName

class PersonShowDetails : ArrayList<PersonShowDetails.PersonShowDetailsItem>(){
    data class PersonShowDetailsItem(
        @SerializedName("self")
        val self: Boolean,
        @SerializedName("voice")
        val voice: Boolean,
        @SerializedName("_links")
        val links: Links,
        @SerializedName("_embedded")
        val embedded: Embedded
    ) {
        data class Links(
            @SerializedName("show")
            val show: Show,
            @SerializedName("character")
            val character: Character
        ) {
            data class Show(
                @SerializedName("href")
                val href: String
            )
    
            data class Character(
                @SerializedName("href")
                val href: String
            )
        }
    
        data class Embedded(
            @SerializedName("show")
            val show: Show
        ) {
            data class Show(
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
                val runtime: Any,
                @SerializedName("premiered")
                val premiered: Any,
                @SerializedName("officialSite")
                val officialSite: String,
                @SerializedName("schedule")
                val schedule: Schedule,
                @SerializedName("rating")
                val rating: Rating,
                @SerializedName("weight")
                val weight: Int,
                @SerializedName("network")
                val network: Any,
                @SerializedName("webChannel")
                val webChannel: WebChannel,
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
                val links: Links
            ) {
                data class Image(
                    @SerializedName("medium")
                    val medium: String,
                    @SerializedName("original")
                    val original: String
                )

                data class Schedule(
                    @SerializedName("time")
                    val time: String,
                    @SerializedName("days")
                    val days: List<Any>
                )
    
                data class Rating(
                    @SerializedName("average")
                    val average: Any
                )
    
                data class WebChannel(
                    @SerializedName("id")
                    val id: Int,
                    @SerializedName("name")
                    val name: String,
                    @SerializedName("country")
                    val country: Any
                )
    
                data class Externals(
                    @SerializedName("tvrage")
                    val tvrage: Any,
                    @SerializedName("thetvdb")
                    val thetvdb: Any,
                    @SerializedName("imdb")
                    val imdb: Any
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
}
