package com.sopt.peekabookaos.data.source.local

import android.content.SharedPreferences
import javax.inject.Inject

class LocalPrefDataSource @Inject constructor(
    private val prefs: SharedPreferences
) {
    fun clearLocalPref() {
        with(prefs.edit()) {
            clear()
            commit()
        }
    }
}
