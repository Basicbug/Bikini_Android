/*
 * SettingItemsProvider.kt 2021. 4. 3
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.settings

import com.example.bikini_android.R
import com.example.bikini_android.app.AppResources
import com.example.bikini_android.manager.login.LoginManagerProxy
import com.example.bikini_android.ui.common.item.ItemViewModel
import com.example.bikini_android.ui.holder.NavigationHelperImpl
import com.example.bikini_android.ui.settings.item.SettingsContentItemViewModel
import com.example.bikini_android.ui.settings.item.SettingsDivideLineItemViewModel
import com.example.bikini_android.ui.settings.item.SettingsUserItemViewModel

/**
 * @author MyeongKi
 */

abstract class SettingsItemsProvider {
    fun createMainSettingItems(navigationHelper: NavigationHelperImpl?): List<ItemViewModel> {
        return mutableListOf<ItemViewModel>().apply {
            addAll(createCommonSettingItems(navigationHelper))
            addAll(createMainSubSettingItems(navigationHelper))
        }
    }

    private fun createCommonSettingItems(navigationHelper: NavigationHelperImpl?): List<ItemViewModel> {
        return listOf(
            SettingsUserItemViewModel.Builder(LoginManagerProxy.userName)
                .setOnClickAction {
                    navigationHelper?.navigateToProfileDetail()
                }.build(),
            SettingsDivideLineItemViewModel(),
            SettingsContentItemViewModel.Builder(AppResources.getString(R.string.settings_image_title))
                .setOnClickAction {
                    navigationHelper?.navigateToSettings(SettingsFragment.makeBundle(SettingsType.IMAGE))
                }.build()

        )
    }

    protected abstract fun createMainSubSettingItems(navigationHelper: NavigationHelperImpl?): List<ItemViewModel>
}