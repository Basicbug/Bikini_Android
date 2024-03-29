/*
 * DeveloperSetting.kt 2021. 2. 19
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.settings

import com.example.bikini_android.R
import com.basicbug.core.app.AppResources
import com.basicbug.core.ui.settings.DeveloperSetting
import com.example.bikini_android.network.NetworkConstants
import com.example.bikini_android.network.ServerType
import com.basicbug.core.util.ktx.get

/**
 * @author qwebnm7788
 */
object DeveloperSettingImpl : DeveloperSetting {
    private fun getServerType(): ServerType {
        with(AppResources.getSharedPref()) {
            val serverType = get(AppResources.getString(R.string.perf_server_type), "DEV")
            return ServerType.valueOf(serverType)
        }
    }

    override fun getBaseDomain(): String {
        return when (getServerType()) {
            ServerType.DEV -> NetworkConstants.DEV_URL
            ServerType.PRD -> NetworkConstants.PRD_URL
        }
    }
}
