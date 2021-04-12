/*
 * BikiniApp.kt 2020. 10. 27
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.app

import android.app.Application
import android.content.Context
import com.example.bikini_android.ui.settings.FlipperSettingImpl

/**
 * @author MyeongKi
 */
class BikiniApp : Application() {

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        FlipperSettingImpl.initFlipperSetting(this)
    }

    companion object {
        private var instance: BikiniApp? = null
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}