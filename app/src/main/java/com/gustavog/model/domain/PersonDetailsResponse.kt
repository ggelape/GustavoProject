package com.gustavog.model.domain


import com.google.gson.annotations.SerializedName

data class PersonDetailsResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("country")
    val country: Country,
    @SerializedName("birthday")
    val birthday: String,
    @SerializedName("deathday")
    val deathday: Any,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("image")
    val image: Image,
    @SerializedName("_links")
    val links: Links,
) {
    data class Country(
        @SerializedName("name")
        val name: String,
        @SerializedName("code")
        val code: String,
        @SerializedName("timezone")
        val timezone: String
    )

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
