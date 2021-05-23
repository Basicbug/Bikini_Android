package com.example.bikini_android.manager

import android.content.Context
import android.content.SharedPreferences
import com.example.bikini_android.app.AppResources

/**
 * @author bsgreentea
 */
object PreferenceManager {

    private const val PREFERENCE_NAME = "bikini"

    private fun getPreferences(): SharedPreferences {
        return AppResources.getContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    fun setBoolean(key: String, value: Boolean) {
        getPreferences().edit().putBoolean(key, value).apply()
    }

    fun setString(key: String, value: String) {
        getPreferences().edit().putString(key, value).apply()
    }

    fun getBoolean(key: String) = getPreferences().getBoolean(key, false)
}
