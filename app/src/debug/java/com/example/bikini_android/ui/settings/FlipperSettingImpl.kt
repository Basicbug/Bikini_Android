package com.example.bikini_android.ui.settings

import com.example.bikini_android.app.AppResources
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader

/**
 * @author bsgreentea
 */
class FlipperSettingImpl {

    private val flipperNetworkPlugin = NetworkFlipperPlugin()

    private fun initFlipperSetting() {

        SoLoader.init(AppResources.getContext(), false)
        AndroidFlipperClient.getInstance(AppResources.getContext()).apply {
            addPlugin(
                InspectorFlipperPlugin(
                    AppResources.getContext(),
                    DescriptorMapping.withDefaults()
                )
            )
            addPlugin(flipperNetworkPlugin)
        }.start()
    }

    fun getFlipperNetworkPlugin(): FlipperOkhttpInterceptor {
        initFlipperSetting()
        return FlipperOkhttpInterceptor(flipperNetworkPlugin)
    }
}
