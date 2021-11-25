/*
 * PreferenceManager.kt 2021. 12. 2
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.core.manager

import android.content.Context
import android.content.SharedPreferences
import com.basicbug.core.app.AppResources

/**
 * @author bsgreentea
 */
abstract class PreferenceManager {

    protected fun getPreferences(): SharedPreferences {
        return AppResources.getContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    fun getString(key: String): String {
        return getPreferences().getString(key, "") ?: ""
    }

    fun setBoolean(key: String, value: Boolean) {
        getPreferences().edit().putBoolean(key, value).apply()
    }

    fun setString(key: String, value: String) {
        getPreferences().edit().putString(key, value).apply()
    }

    fun getBoolean(key: String) = getPreferences().getBoolean(key, false)

    companion object{
        private const val PREFERENCE_NAME = "basicbug"
    }
}