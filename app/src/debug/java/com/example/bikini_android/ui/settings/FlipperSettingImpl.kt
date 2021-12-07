package com.example.bikini_android.ui.settings

import com.basicbug.core.app.AppResources
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.leakcanary2.FlipperLeakListener
import com.facebook.flipper.plugins.leakcanary2.LeakCanary2FlipperPlugin
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader
import leakcanary.LeakCanary

/**
 * @author bsgreentea
 */
class FlipperSettingImpl {

    private val flipperNetworkPlugin = NetworkFlipperPlugin()

    private fun initFlipperSetting() {

        SoLoader.init(AppResources.getContext(), false)
        LeakCanary.config = LeakCanary.config.copy(
            onHeapAnalyzedListener = FlipperLeakListener()
        )
        AndroidFlipperClient.getInstance(AppResources.getContext()).apply {
            addPlugin(
                InspectorFlipperPlugin(
                    AppResources.getContext(),
                    DescriptorMapping.withDefaults()
                )
            )
            addPlugin(DatabasesFlipperPlugin(AppResources.getContext()))
            addPlugin(SharedPreferencesFlipperPlugin(AppResources.getContext()))
            addPlugin(LeakCanary2FlipperPlugin())
            addPlugin(flipperNetworkPlugin)
        }.start()
    }

    fun getFlipperNetworkPlugin(): FlipperOkhttpInterceptor {
        initFlipperSetting()
        return FlipperOkhttpInterceptor(flipperNetworkPlugin)
    }
}