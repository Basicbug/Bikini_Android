/*
 * SettingItemsProviderImpl.kt 2021. 4. 3
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.settings

import com.basicbug.core.ui.item.ItemViewModel

/**
 * @author MyeongKi
 */
object SettingsItemProviderImpl : SettingsItemsProvider() {
    override fun createMainSubSettingItems(): List<ItemViewModel> {
        return emptyList()
    }
}