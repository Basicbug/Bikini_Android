/*
 * SettingsImageCompressionConfigItemProvider.kt 2021. 6. 13
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.settings.image

import com.example.bikini_android.R
import com.example.bikini_android.app.AppResources
import com.example.bikini_android.manager.PreferenceManager
import com.example.bikini_android.ui.common.item.ItemViewModel
import com.example.bikini_android.ui.settings.item.SettingsCheckItemViewModel
import com.example.bikini_android.ui.settings.item.SettingsTitleItemViewModel
import com.example.bikini_android.util.bus.RxAction
import com.jakewharton.rxrelay2.Relay

/**
 * @author MyeongKi
 */
object SettingsImageCompressionConfigItemProvider {
    fun createImageCompressionHeadItem(): ItemViewModel {
        return SettingsTitleItemViewModel.Builder(AppResources.getStringResId(R.string.settings_image_compression_title))
            .build()
    }

    fun createCompressionItems(itemEventRelay: Relay<RxAction>): List<SettingsCheckItemViewModel> {
        val result = mutableListOf<SettingsCheckItemViewModel>()
        for (imageCompressionRate in SettingsImageCompressionRate.values()) {
            result.add(
                SettingsCheckItemViewModel.Builder(AppResources.getStringResId(imageCompressionRate.stringResId))
                    .setOnClickAction {
                        itemEventRelay.accept(
                            SettingsImageCompressionViewModel.SettingsImageCompressionRateEvent(
                                imageCompressionRate
                            )
                        )
                    }
                    .build()
            )
        }
        return result
    }
}