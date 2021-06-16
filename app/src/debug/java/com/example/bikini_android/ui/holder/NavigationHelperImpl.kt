/*
 * NavigationHelperImpl.kt 2021. 4. 4
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.holder

import android.os.Bundle
import com.example.bikini_android.R
import com.example.bikini_android.util.bus.RxAction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jakewharton.rxrelay2.Relay

/**
 * @author MyeongKi
 */
class NavigationHelperImpl(
    bottomNav: BottomNavigationView,
    activity: MainHolderActivity?,
    itemRelay: Relay<RxAction>
) : NavigationHelper(bottomNav, activity, itemRelay) {

    fun navigateToSettingsFlavorOnly(bundle: Bundle) {
        getNavController().navigate(R.id.action_flavor_only_setting, bundle)
    }
    fun navigateToSettingsApi(): () -> Unit = {
        getNavController().navigate(R.id.action_settings_api)
    }
}