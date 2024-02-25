package com.sopt.peekabookaos.data.source.local

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class LocalTokenDataSource @Inject constructor(
    private val prefs: SharedPreferences
) {
    var accessToken: String
        set(value) = prefs.edit { putString(ACCESS_TOKEN, value) }
        get() = prefs.getString(ACCESS_TOKEN, "") ?: ""

    var refreshToken: String
        set(value) = prefs.edit { putString(REFRESH_TOKEN, value) }
        get() = prefs.getString(REFRESH_TOKEN, "") ?: ""

    var fcmToken: String
        set(value) = prefs.edit { putString(FCM_TOKEN, value) }
        get() = prefs.getString(FCM_TOKEN, "") ?: ""

    companion object {
        private const val ACCESS_TOKEN = "access_token"
        private const val REFRESH_TOKEN = "refresh_token"
        private const val FCM_TOKEN = "fcm_token"
    }
}
