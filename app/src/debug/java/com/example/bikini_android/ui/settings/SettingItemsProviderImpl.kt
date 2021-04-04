/*
 * SettingItemsProviderImpl.kt 2021. 4. 3
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.settings

import com.example.bikini_android.ui.common.item.ItemViewModel
import com.example.bikini_android.ui.holder.NavigationHelper
import com.example.bikini_android.ui.settings.item.SettingContentItemViewModel
import com.example.bikini_android.ui.settings.item.SettingTitleItemViewModel

/**
 * @author MyeongKi
 */
object SettingItemsProviderImpl : SettingItemsProvider() {
    override fun createMainSettingItemsFlavorOnly(navigationHelper: NavigationHelper?): List<ItemViewModel> {
        return listOf(
            SettingTitleItemViewModel.Builder("Dev Settings").build(),
            SettingContentItemViewModel.Builder("api").setOnClickAction {

            }.build()
        )
    }
}