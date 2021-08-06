/*
 * SettingItemsProviderImpl.kt 2021. 4. 3
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.settings

import com.example.bikini_android.R
import com.example.bikini_android.app.AppResources
import com.example.bikini_android.ui.common.item.ItemViewModel
import com.example.bikini_android.ui.holder.NavigationHelperImpl
import com.example.bikini_android.ui.settings.item.SettingsContentItemViewModel
import com.example.bikini_android.ui.settings.item.SettingsDivideLineItemViewModel
import com.example.bikini_android.ui.settings.item.SettingsTitleItemViewModel

/**
 * @author MyeongKi
 */
object SettingsItemProviderImpl : SettingsItemsProvider() {
    override fun createMainSubSettingItems(navigationHelper: NavigationHelperImpl?): List<ItemViewModel> {
        return listOf(
            SettingsDivideLineItemViewModel(),
            SettingsContentItemViewModel.Builder(AppResources.getString(R.string.settings_dev_title))
                .setOnClickAction {
                    navigationHelper?.navigateToSettingsFlavorOnly(SettingsFragment.makeBundle(SettingsType.FLAVOR_ONLY))
                }.build()
        )
    }

    fun createSettingItemsFlavorOnly(navigationHelper: NavigationHelperImpl?): List<ItemViewModel> {
        return listOf(
            SettingsTitleItemViewModel.Builder(AppResources.getString(R.string.settings_network_title))
                .build(),
            SettingsContentItemViewModel.Builder(AppResources.getString(R.string.settings_api_title))
                .setOnClickAction {
                    navigationHelper?.navigateToSettingsApi()?.invoke()
                }.build()
        )
    }
}