/*
 * BikiniApp.kt 2020. 10. 27
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.app

import android.app.Application
import android.content.Context
import com.example.bikini_android.R
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

/**
 * @author MyeongKi
 */
@HiltAndroidApp
class BikiniApp : Application() {

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, AppResources.getString(R.string.kakao_native_app_key))
    }

    companion object {
        private var instance: BikiniApp? = null
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}