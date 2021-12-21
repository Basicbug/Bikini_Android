/*
 * BikiniApp.kt 2020. 10. 27
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.app

import com.basicbug.core.app.AppResources
import com.basicbug.core.app.BasicBugApp
import com.example.bikini_android.R
import com.example.bikini_android.util.map.LocationUtils
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

/**
 * @author MyeongKi
 */
@HiltAndroidApp
class BikiniApp : BasicBugApp() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, AppResources.getString(R.string.kakao_native_app_key))
        LocationUtils.initCurrentLocationEvent()
    }
}