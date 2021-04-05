/*
 * BikiniApp.kt 2020. 10. 27
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.app

import android.app.Application
import android.content.Context
import com.example.bikini_android.BuildConfig
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader

/**
 * @author MyeongKi
 */
class BikiniApp : Application() {

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        SoLoader.init(this, false)

        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
            val client = AndroidFlipperClient.getInstance(this)
            client.apply {
                addPlugin(InspectorFlipperPlugin(this@BikiniApp, DescriptorMapping.withDefaults()))
                addPlugin(networkFlipperPlugin)
            }
            client.start()
        }
    }

    companion object {
        private var instance: BikiniApp? = null
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }

        val networkFlipperPlugin = NetworkFlipperPlugin()
    }
}