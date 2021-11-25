/*
 * SettingsImageCompressionConfigItemProvider.kt 2021. 6. 13
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.settings.image

import com.example.bikini_android.R
import com.basicbug.core.app.AppResources
import com.basicbug.core.ui.item.ItemViewModel
import com.example.bikini_android.ui.settings.item.SettingsCheckItemViewModel
import com.example.bikini_android.ui.settings.item.SettingsTitleItemViewModel
import com.basicbug.core.util.bus.RxAction
import com.jakewharton.rxrelay2.Relay

/**
 * @author MyeongKi
 */
object SettingsImageCompressionConfigItemProvider {
    fun createImageCompressionHeadItem(): ItemViewModel {
        return SettingsTitleItemViewModel.Builder(AppResources.getString(R.string.settings_image_compression_title))
            .build()
    }

    fun createCompressionItems(itemEventRelay: Relay<RxAction>): List<SettingsCheckItemViewModel> {
        val result = mutableListOf<SettingsCheckItemViewModel>()
        for (imageCompressionRate in SettingsImageCompressionRate.values()) {
            result.add(
                SettingsCheckItemViewModel.Builder(AppResources.getString(imageCompressionRate.stringResId))
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