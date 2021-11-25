/*
 * SettingItemsProviderImpl.kt 2021. 4. 3
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.settings

import com.example.bikini_android.R
import com.basicbug.core.app.AppResources
import com.example.bikini_android.ui.common.item.ItemViewModel
import com.example.bikini_android.ui.holder.NavigationHelperImpl
import com.example.bikini_android.ui.settings.item.SettingsContentItemViewModel
import com.example.bikini_android.ui.settings.item.SettingsDivideLineItemViewModel
import com.example.bikini_android.ui.settings.item.SettingsTitleItemViewModel

/**
 * @author MyeongKi
 */
object SettingsItemProviderImpl : SettingsItemsProvider() {
    override fun createMainSubSettingItems(): List<ItemViewModel> {
        return listOf(
            SettingsDivideLineItemViewModel(),
            SettingsContentItemViewModel.Builder(AppResources.getString(R.string.settings_dev_title))
                .setOnClickAction {
                    NavigationHelperImpl.navigateToSettingsFlavorOnly(SettingsFragment.makeBundle(SettingsType.FLAVOR_ONLY))
                }.build()
        )
    }

    fun createSettingItemsFlavorOnly(): List<ItemViewModel> {
        return listOf(
            SettingsTitleItemViewModel.Builder(AppResources.getString(R.string.settings_network_title))
                .build(),
            SettingsContentItemViewModel.Builder(AppResources.getString(R.string.settings_api_title))
                .setOnClickAction {
                    NavigationHelperImpl.navigateToSettingsApi().invoke()
                }.build()
        )
    }
}