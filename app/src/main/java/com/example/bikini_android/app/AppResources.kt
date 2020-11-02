/*
 * AppResources.kt 2020. 10. 27
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.app

import android.content.Context
import android.content.res.Resources
import androidx.annotation.StringRes

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

}