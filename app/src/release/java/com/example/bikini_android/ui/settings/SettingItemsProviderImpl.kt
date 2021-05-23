/*
 * SettingItemsProviderImpl.kt 2021. 4. 3
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.settings

import com.example.bikini_android.ui.common.item.ItemViewModel
import com.example.bikini_android.ui.holder.NavigationHelperImpl

/**
 * @author MyeongKi
 */
object SettingItemsProviderImpl : SettingItemsProvider() {
    override fun createMainSubSettingItems(navigationHelper: NavigationHelperImpl?): List<ItemViewModel> {
        return emptyList()
    }
}
