/*
 * DeveloperSetting.kt 2021. 2. 11
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 */

package com.example.bikini_android.ui.settings

import com.example.bikini_android.app.AppResources
import com.example.bikini_android.network.NetworkConstants
import com.example.bikini_android.network.ServerType
import com.example.bikini_android.util.ktx.get
import com.example.bikini_android.util.ktx.pref

/**
 * @author qwebnm7788
 */
object DeveloperSetting {

    private fun isDevMode(): Boolean {
        return AppResources.getContext().pref().get(SettingsFragment.PREF_DEV_MODE, false)
    }

    private fun getServerType(): ServerType {
        if (isDevMode()) {
            with (AppResources.getContext().pref()) {
                val serverType = get(SettingsFragment.PREF_SERVER_TYPE, "DEV")
                return ServerType.valueOf(serverType)
            }
        } else {
            return ServerType.DEV
        }
    }

    fun getBaseDomain(): String {
        return when (getServerType()) {
            ServerType.DEV -> NetworkConstants.DEV_URL
            ServerType.PRD -> NetworkConstants.PRD_URL
        }
    }
}
