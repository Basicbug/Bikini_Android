/*
 * SettingsImageViewModel.kt 2021. 6. 13
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.settings.image

import com.example.bikini_android.ui.common.item.ItemViewModel
import com.example.bikini_android.ui.holder.NavigationHelperImpl
import com.example.bikini_android.ui.settings.SettingsViewModel

/**
 * @author MyeongKi
 */
class SettingsImageViewModel(private val navigationHelper: NavigationHelperImpl? = null) :
    SettingsViewModel() {
    override fun getSettingsItemViewModels(): List<ItemViewModel> {
        return SettingsImageConfigItemProvider.createImageSettingItems(
            navigationHelper
        )
    }
}