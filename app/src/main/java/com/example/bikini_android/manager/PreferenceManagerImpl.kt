/*
 * PreferenceManagerImpl.kt 2021. 12. 2
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.manager

import com.basicbug.core.app.AppResources
import com.basicbug.core.manager.PreferenceManager
import com.example.bikini_android.ui.settings.image.SettingsImageCompressionRate
import com.example.bikini_android.R

/**
 * @author MyeongKi
 */
object PreferenceManagerImpl:PreferenceManager() {
    fun setImageCompressionRate(value: SettingsImageCompressionRate) {
        getPreferences().edit()
            .putInt(AppResources.getString(R.string.image_compression_rate), value.rate)
            .apply()
    }

    fun getImageCompressionRate(): SettingsImageCompressionRate {
        val compressionRate = getPreferences().getInt(
            AppResources.getString(R.string.image_compression_rate),
            SettingsImageCompressionRate.NORMAL_QUALITY.rate
        )
        return SettingsImageCompressionRate.fromRate(compressionRate)
    }
}