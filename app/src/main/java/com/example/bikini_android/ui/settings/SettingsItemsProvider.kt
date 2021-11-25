/*
 * SettingItemsProvider.kt 2021. 4. 3
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.settings

import com.example.bikini_android.R
import com.basicbug.core.app.AppResources
import com.example.bikini_android.manager.login.LoginManagerProxy
import com.basicbug.core.ui.item.ItemViewModel
import com.example.bikini_android.ui.holder.NavigationHelperImpl
import com.example.bikini_android.ui.settings.item.SettingsContentItemViewModel
import com.example.bikini_android.ui.settings.item.SettingsDivideLineItemViewModel
import com.example.bikini_android.ui.settings.item.SettingsUserItemViewModel

/**
 * @author MyeongKi
 */

abstract class SettingsItemsProvider {
    fun createMainSettingItems(): List<ItemViewModel> {
        return mutableListOf<ItemViewModel>().apply {
            addAll(createCommonSettingItems())
            addAll(createMainSubSettingItems())
        }
    }

    private fun createCommonSettingItems(): List<ItemViewModel> {
        return listOf(
            SettingsUserItemViewModel.Builder(LoginManagerProxy.userName)
                .setOnClickAction {
                    NavigationHelperImpl.navigateToProfileDetail()
                }.build(),
            SettingsDivideLineItemViewModel(),
            SettingsContentItemViewModel.Builder(AppResources.getString(R.string.settings_image_title))
                .setOnClickAction {
                    NavigationHelperImpl.navigateToSettings(SettingsFragment.makeBundle(SettingsType.IMAGE))
                }.build()

        )
    }

    protected abstract fun createMainSubSettingItems(): List<ItemViewModel>
}