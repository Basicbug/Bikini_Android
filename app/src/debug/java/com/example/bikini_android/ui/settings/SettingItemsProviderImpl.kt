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
import com.example.bikini_android.ui.settings.item.SettingContentItemViewModel
import com.example.bikini_android.ui.settings.item.SettingDivideLineItemViewModel
import com.example.bikini_android.ui.settings.item.SettingTitleItemViewModel

/**
 * @author MyeongKi
 */
object SettingItemsProviderImpl : SettingItemsProvider() {
    override fun createMainSubSettingItems(navigationHelper: NavigationHelperImpl?): List<ItemViewModel> {
        return listOf(
            SettingDivideLineItemViewModel(),
            SettingContentItemViewModel.Builder(AppResources.getStringResId(R.string.setting_dev_title))
                .setOnClickAction {
                    navigationHelper?.navigateToSettingsFlavorOnly()?.invoke()
                }.build()
        )
    }

    fun createSettingItemsFlavorOnly(navigationHelper: NavigationHelperImpl?): List<ItemViewModel> {
        return listOf(
            SettingTitleItemViewModel.Builder(AppResources.getStringResId(R.string.setting_network_title))
                .build(),
            SettingContentItemViewModel.Builder(AppResources.getStringResId(R.string.setting_api_title))
                .setOnClickAction {
                    navigationHelper?.navigateToSettingsApi()?.invoke()
                }.build()
        )
    }
}