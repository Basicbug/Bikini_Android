/*
 * Logger.kt 2020. 11. 3
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 */

package com.example.bikini_android.util.logging

import android.util.Log
import androidx.multidex.BuildConfig

/**
 * @author qwebnm7788
 */

const val APP_TAG = "BIKINI-"
const val BUILD_TYPE = BuildConfig.BUILD_TYPE

class Logger {

    var TAG = ""
    val PRE_TAG = "$APP_TAG$TAG"

    inline fun verbose(forceTag: String = "", message: () -> String) {
        Log.v("$PRE_TAG$forceTag", message())
    }

    inline fun info(forceTag: String = "", message: () -> String) {
        Log.i("$PRE_TAG$forceTag", message())
    }

    inline fun debug(forceTag: String = "", message: () -> String) {
        Log.d("$PRE_TAG$forceTag", message())
    }

    inline fun warn(forceTag: String = "", message: () -> String) {
        Log.w("$PRE_TAG$forceTag", message())
    }

    inline fun error(forceTag: String = "", message: () -> String) {
        Log.e("$PRE_TAG$forceTag", message())
    }
}