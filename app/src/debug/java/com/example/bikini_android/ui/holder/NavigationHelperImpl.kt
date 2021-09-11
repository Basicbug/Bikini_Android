/*
 * NavigationHelperImpl.kt 2021. 4. 4
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.holder

import android.os.Bundle
import com.example.bikini_android.R

/**
 * @author MyeongKi
 */
object NavigationHelperImpl : NavigationHelper() {

    fun navigateToSettingsFlavorOnly(bundle: Bundle) {
        getNavController()?.navigate(R.id.action_flavor_only_setting, bundle)
    }

    fun navigateToSettingsApi(): () -> Unit = {
        getNavController()?.navigate(R.id.action_settings_api)
    }
}