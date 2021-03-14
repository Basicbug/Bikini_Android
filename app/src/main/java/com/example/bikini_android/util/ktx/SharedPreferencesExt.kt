/*
 * SharedPreferencesExt.kt 2021. 2. 11
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 */

package com.example.bikini_android.util.ktx

import android.content.Context
import android.content.SharedPreferences

/**
 * @author qwebnm7788
 */

private const val BIKINI_SHARED_PREF = "Bikini_prefs"

fun Context.pref(): SharedPreferences {
    return getSharedPreferences(BIKINI_SHARED_PREF, Context.MODE_PRIVATE)
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T> SharedPreferences.get(key: String, defaultValue: T): T {
    when (T::class) {
        Boolean::class -> return getBoolean(key, defaultValue as Boolean) as T
        Float::class -> return getFloat(key, defaultValue as Float) as T
        Int::class -> return getInt(key, defaultValue as Int) as T
        Long::class -> return getLong(key, defaultValue as Long) as T
        String::class -> return getString(key, defaultValue as String) as T
        else -> {
            if (defaultValue is Set<*>) {
                return getStringSet(key, defaultValue as Set<String>) as T
            }
        }
    }

    return defaultValue
}

inline fun <reified T> SharedPreferences.put(key: String, value: T) {
    val editor = edit()

    when (T::class) {
        Boolean::class -> editor.putBoolean(key, value as Boolean)
        Float::class -> editor.putFloat(key, value as Float)
        Int::class -> editor.putInt(key, value as Int)
        Long::class -> editor.putLong(key, value as Long)
        String::class -> editor.putString(key, value as String)
        else -> {
            if (value is Set<*>) {
                editor.putStringSet(key, value as Set<String>)
            }
        }
    }

    editor.apply()
}
