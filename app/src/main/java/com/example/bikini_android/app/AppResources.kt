/*
 * AppResources.kt 2020. 10. 27
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.app

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import androidx.annotation.StringRes
import com.example.bikini_android.util.ktx.pref

/**
 * @author MyeongKi
 */

object AppResources {
    @JvmStatic
    fun getContext(): Context {
        return BikiniApp.applicationContext()
    }

    @JvmStatic
    fun getResources(): Resources {
        return getContext().resources
    }

    @JvmStatic
    fun getStringResId(@StringRes resId: Int): String {
        return getResources().getString(resId)
    }

    @JvmStatic
    fun getSharedPref(): SharedPreferences {
        return getContext().pref()
    }

    @JvmStatic
    fun getStringResId(@StringRes resId: Int, vararg format: Any): String {
        return getResources().getString(resId, *format)
    }
}
