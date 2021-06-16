/*
 * SettingsImageCompressionRate.kt 2021. 6. 13
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.settings.image

import androidx.annotation.StringRes
import com.example.bikini_android.R

/**
 * @author MyeongKi
 */
enum class SettingsImageCompressionRate(
    val rate: Int,
    @StringRes val stringResId: Int,
    val index: Int
) {
    BAD_QUALITY(50, R.string.settings_image_compression_bad_quality, 0),
    NORMAL_QUALITY(75, R.string.settings_image_compression_normal_quality, 1),
    BEST_QUALITY(100, R.string.settings_image_compression_best_quality, 2);

    companion object {
        fun fromRate(rate: Int): SettingsImageCompressionRate {
            for (type in values()) {
                if (type.rate == rate) {
                    return type
                }
            }
            return NORMAL_QUALITY
        }
    }
}