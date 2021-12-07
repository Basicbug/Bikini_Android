/*
 * Logger.kt 2021. 12. 2
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.core.util.logging

import android.util.Log
import com.basicbug.core.BuildConfig

/**
 * @author qwebnm7788
 */

const val APP_TAG = "BASIC_BUG-"
const val BUILD_TYPE = BuildConfig.BUILD_TYPE

class Logger {

    var TAG = ""
    val PRE_TAG
        get() = "$APP_TAG$TAG"

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