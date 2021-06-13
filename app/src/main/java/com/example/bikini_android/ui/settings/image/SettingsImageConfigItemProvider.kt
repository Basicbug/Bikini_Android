/*
 * SettingsImageConfigItemProvider.kt 2021. 6. 13
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.settings.image

import com.example.bikini_android.R
import com.example.bikini_android.app.AppResources
import com.example.bikini_android.ui.common.item.ItemViewModel
import com.example.bikini_android.ui.holder.NavigationHelperImpl
import com.example.bikini_android.ui.settings.item.SettingsContentItemViewModel
import com.example.bikini_android.ui.settings.item.SettingsTitleItemViewModel

/**
 * @author MyeongKi
 */
object SettingsImageConfigItemProvider {
    fun createImageSettingItems(navigationHelper: NavigationHelperImpl?): List<ItemViewModel> {
        return mutableListOf<ItemViewModel>().apply {
            addAll(createItems(navigationHelper))
        }
    }

    private fun createItems(navigationHelper: NavigationHelperImpl?): List<ItemViewModel> {
        return listOf(
            SettingsTitleItemViewModel.Builder(AppResources.getStringResId(R.string.settings_image_title))
                .build(),
            SettingsContentItemViewModel.Builder(AppResources.getStringResId(R.string.settings_image_compression_title))
                .setOnClickAction {
                    navigationHelper?.navigateToImageCompressionSettings()
                }.build()
        )
    }

}