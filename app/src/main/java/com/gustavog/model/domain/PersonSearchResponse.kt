package com.gustavog.model.domain


import com.google.gson.annotations.SerializedName

class PersonSearchResponse : ArrayList<PersonSearchResponse.PersonSearchResponseItem>(){
    data class PersonSearchResponseItem(
        @SerializedName("score")
        val score: Double,
        @SerializedName("person")
        val person: Person
    ) {
        data class Person(
            @SerializedName("id")
            val id: Int,
            @SerializedName("url")
            val url: String,
            @SerializedName("name")
            val name: String,
            @SerializedName("country")
            val country: Any,
            @SerializedName("birthday")
            val birthday: Any,
            @SerializedName("deathday")
            val deathday: Any,
            @SerializedName("gender")
            val gender: String,
            @SerializedName("image")
            val image: Image,
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
