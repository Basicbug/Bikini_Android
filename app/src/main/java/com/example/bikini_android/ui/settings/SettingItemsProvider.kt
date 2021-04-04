/*
 * SettingItemsProvider.kt 2021. 4. 3
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.settings

import com.example.bikini_android.ui.common.item.ItemViewModel
import com.example.bikini_android.ui.holder.NavigationHelper

/**
 * @author MyeongKi
 */
//추상화 시켜서 기본적인 아이템을 주입
abstract class SettingItemsProvider {
    fun createMainSettingItems(navigationHelper: NavigationHelper?): List<ItemViewModel> {
        return mutableListOf<ItemViewModel>().apply {
            addAll(createMainSettingItemsFlavorOnly(navigationHelper))
        }
    }

    protected abstract fun createMainSettingItemsFlavorOnly(navigationHelper: NavigationHelper?): List<ItemViewModel>
}