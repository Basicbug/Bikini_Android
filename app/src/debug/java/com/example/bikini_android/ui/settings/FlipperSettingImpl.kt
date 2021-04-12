package com.example.bikini_android.ui.settings

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader
import okhttp3.OkHttpClient

/**
 * @author bsgreentea
 */
object FlipperSettingImpl {

    private val flipperNetworkPlugin = NetworkFlipperPlugin()

    fun initFlipperSetting(app: Application) {

        SoLoader.init(app, false)

        val client = AndroidFlipperClient.getInstance(app)
        client.apply {
            addPlugin(InspectorFlipperPlugin(app, DescriptorMapping.withDefaults()))
            addPlugin(flipperNetworkPlugin)
        }
        client.start()
    }

    fun addFlipperNetworkPlugin(builder: OkHttpClient.Builder) =
        builder.addNetworkInterceptor(FlipperOkhttpInterceptor(flipperNetworkPlugin))
}