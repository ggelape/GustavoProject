package com.gustavog.model.storage

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.gustavog.model.domain.ShowDetailsResponse

class DataStorage(private val context: Context) {

    private companion object {

        const val DATA_STORAGE_LOCATION = "data_storage"
        const val SELECTED_EPISODE = "selected_episode"
        const val FAVORITE_LIST = "favorite_list"
        const val USER_PIN = "user_pin"
        const val USER_FINGERPRINT_AUTH_KEY = "user_fingerprint_auth"
    }

    private fun getStorage(): SharedPreferences =
        context.getSharedPreferences(DATA_STORAGE_LOCATION, Context.MODE_PRIVATE)

    fun getSelectedEpisode(): ShowDetailsResponse.Embedded.Episode? {

        val value = getStorage().getString(SELECTED_EPISODE, null)

        value?.let {
            return Gson().fromJson(value, ShowDetailsResponse.Embedded.Episode::class.java)
        }

        return null
    }

    fun saveSelectedEpisode(episode: ShowDetailsResponse.Embedded.Episode) {
        getStorage()
            .edit()
            .putString(
                SELECTED_EPISODE, Gson().toJson(
                    episode,
                    ShowDetailsResponse.Embedded.Episode::class.java
                )
            )
            .apply()
    }

    fun getFavorites(): ArrayList<ShowDetailsResponse>? {
        val value = getStorage().getString(FAVORITE_LIST, null)
        return Gson().fromJson(value, object : TypeToken<ArrayList<ShowDetailsResponse>>() {}.type)
    }

    fun saveFavorite(series: ShowDetailsResponse) {
        val value = getStorage().getString(FAVORITE_LIST, null)
        var list: ArrayList<ShowDetailsResponse>? =
            Gson().fromJson(value, object : TypeToken<ArrayList<ShowDetailsResponse>>() {}.type)
        if (list == null) {
            list = ArrayList()
        }
        list.add(series)

        getStorage()
            .edit()
            .putString(FAVORITE_LIST, Gson().toJson(list, object : TypeToken<ArrayList<ShowDetailsResponse>>() {}.type))
            .apply()
    }

    fun removeFavorite(series: ShowDetailsResponse) {
        val value = getStorage().getString(FAVORITE_LIST, null)
        var list: ArrayList<ShowDetailsResponse>? =
            Gson().fromJson(value, object : TypeToken<ArrayList<ShowDetailsResponse>>() {}.type)
        if (list == null) {
            list = ArrayList()
        }
        list.remove(series)

        getStorage()
            .edit()
            .putString(FAVORITE_LIST, Gson().toJson(list, object : TypeToken<ArrayList<ShowDetailsResponse>>() {}.type))
            .apply()
    }

    fun getPin(): String? =
        getStorage().getString(USER_PIN, null)

    fun savePin(pin: String?) {
        getStorage().edit().putString(USER_PIN, pin).apply()
    }

    fun getFingerprintAuthKey(): String? = getStorage().getString(USER_FINGERPRINT_AUTH_KEY, null)

    fun saveFingerprintAuthKey(authKey: String?) {
        getStorage().edit().putString(USER_FINGERPRINT_AUTH_KEY, authKey).apply()
    }
}
