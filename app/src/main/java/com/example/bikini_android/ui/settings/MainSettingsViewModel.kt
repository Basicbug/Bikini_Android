/*
 * MainSettingsViewModel.kt 2021. 6. 13
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.settings

import com.basicbug.core.ui.item.ItemViewModel

/**
 * @author MyeongKi
 */
class MainSettingsViewModel : SettingsViewModel() {
    override fun getSettingsItemViewModels(): List<ItemViewModel> {
        return SettingsItemProviderImpl.createMainSettingItems()
    }
}