package com.sopt.peekabookaos.data.source.local

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class LocalSignedUpDataSource @Inject constructor(
    private val prefs: SharedPreferences
) {
    var isSignedUp: Boolean
        set(value) = prefs.edit { putBoolean(SIGNED_UP, value) }
        get() = prefs.getBoolean(SIGNED_UP, false)

    companion object {
        private const val SIGNED_UP = "signed up"
    }
}
