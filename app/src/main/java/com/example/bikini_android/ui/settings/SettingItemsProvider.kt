/*
 * SettingItemsProvider.kt 2021. 4. 3
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.settings

import com.example.bikini_android.ui.common.item.ItemViewModel
import com.example.bikini_android.ui.holder.NavigationHelperImpl
import com.example.bikini_android.ui.settings.item.SettingTitleItemViewModel

/**
 * @author MyeongKi
 */

abstract class SettingItemsProvider {
    fun createMainSettingItems(navigationHelper: NavigationHelperImpl?): List<ItemViewModel> {
        return mutableListOf<ItemViewModel>().apply {
            addAll(createCommonSettingItems(navigationHelper))
            addAll(createMainSubSettingItems(navigationHelper))
        }
    }

    private fun createCommonSettingItems(navigationHelper: NavigationHelperImpl?): List<ItemViewModel> {
        return listOf(
            SettingTitleItemViewModel.Builder("설정페이지").build(),
        )
    }

    protected abstract fun createMainSubSettingItems(navigationHelper: NavigationHelperImpl?): List<ItemViewModel>
}
