/*
 * SettingsImageConfigItemProvider.kt 2021. 6. 13
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.settings.image

import com.basicbug.core.app.AppResources
import com.basicbug.core.ui.item.ItemViewModel
import com.example.bikini_android.R
import com.example.bikini_android.ui.holder.NavigationHelperImpl
import com.example.bikini_android.ui.settings.SettingsFragment
import com.example.bikini_android.ui.settings.SettingsType
import com.example.bikini_android.ui.settings.item.SettingsContentItemViewModel
import com.example.bikini_android.ui.settings.item.SettingsTitleItemViewModel

/**
 * @author MyeongKi
 */
object SettingsImageConfigItemProvider {
    fun createImageSettingItems(): List<ItemViewModel> {
        return mutableListOf<ItemViewModel>().apply {
            addAll(createItems())
        }
    }

    private fun createItems(): List<ItemViewModel> {
        return listOf(
            SettingsTitleItemViewModel.Builder(AppResources.getString(R.string.settings_image_title))
                .build(),
            SettingsContentItemViewModel.Builder(AppResources.getString(R.string.settings_image_compression_title))
                .setOnClickAction {
                    NavigationHelperImpl.navigateToSettings(SettingsFragment.makeBundle(SettingsType.IMAGE_COMPRESSION))
                }.build()
        )
    }
}