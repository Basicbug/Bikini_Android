package com.example.bikini_android.ui.settings

import android.app.Application
import okhttp3.OkHttpClient

/**
 * @author bsgreentea
 */
object FlipperSettingImpl {

    fun initFlipperSetting(app: Application) {}
    fun addFlipperNetworkPlugin(builder: OkHttpClient.Builder) = builder
}