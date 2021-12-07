/*
 * AppResources.kt 2021. 12. 2
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.core.app

import android.content.ContentResolver
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import androidx.annotation.StringRes
import com.basicbug.core.util.ktx.pref

/**
 * @author MyeongKi
 */

object AppResources {
    @JvmStatic
    fun getContext(): Context {
        return BasicBugApp.applicationContext()
    }

    @JvmStatic
    fun getResources(): Resources {
        return getContext().resources
    }

    @JvmStatic
    fun getString(@StringRes resId: Int): String {
        return getResources().getString(resId)
    }

    @JvmStatic
    fun getSharedPref(): SharedPreferences {
        return getContext().pref()
    }

    @JvmStatic
    fun getString(@StringRes resId: Int, vararg format: Any): String {
        return getResources().getString(resId, *format)
    }

    fun getContentResolver(): ContentResolver {
        return getContext().contentResolver
    }
}