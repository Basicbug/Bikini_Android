/*
 * ActivityExt.kt 2020. 11. 8
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 */

package com.example.bikini_android.util.ktx

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

/**
 * @author qwebnm7788
 */

private const val BIKINI_SHARED_PREF = "Bikini_prefs"

fun Activity.pref(): SharedPreferences {
    return getSharedPreferences(BIKINI_SHARED_PREF, Context.MODE_PRIVATE)
}