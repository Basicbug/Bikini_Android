/*
 * BasicBugApp.kt 2021. 12. 2
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.core.app

import android.app.Application
import android.content.Context

/**
 * @author MyeongKi
 */
abstract class BasicBugApp: Application() {
    init {
        this.also { instance = it }
    }
    companion object {
        private var instance: BasicBugApp? = null
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}