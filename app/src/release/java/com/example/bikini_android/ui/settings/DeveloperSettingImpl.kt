/*
 * DeveloperSetting.kt 2021. 2. 11
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 */

package com.example.bikini_android.ui.settings

import com.example.bikini_android.network.NetworkConstants

/**
 * @author qwebnm7788
 */
object DeveloperSettingImpl : DeveloperSetting {
    override fun getBaseDomain(): String {
        return NetworkConstants.PRD_URL
    }
}
