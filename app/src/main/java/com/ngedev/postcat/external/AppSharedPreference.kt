package com.ngedev.postcat.external

import android.content.SharedPreferences

open class AppSharedPreference(private val prefs: SharedPreferences) {

    companion object {
        const val SHARED_PREFS = "APP_SHARED_PREFS"
        const val ACCESS_TOKEN_KEY = "access_token"
        const val REFRESH_TOKEN_KEY = "access_token"
    }

    fun saveAccessToken(token: String) {
        prefs.edit().putString(ACCESS_TOKEN_KEY, token).apply()
    }

    fun fetchAccessToken(): String? {
        return prefs.getString(ACCESS_TOKEN_KEY, null)
    }

    fun deleteAccessToken() {
        prefs.edit().putString(ACCESS_TOKEN_KEY, null).apply()
    }

    fun setRefreshToken(refreshToken: String) {
        prefs.edit().putString(REFRESH_TOKEN_KEY, refreshToken).apply()
    }

    fun getRefreshToken(): String? = prefs.getString(REFRESH_TOKEN_KEY, null)
}