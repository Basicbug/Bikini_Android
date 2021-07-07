package com.example.bikini_android.manager

import android.content.Context
import android.content.SharedPreferences
import com.example.bikini_android.R
import com.example.bikini_android.app.AppResources
import com.example.bikini_android.ui.settings.image.SettingsImageCompressionRate

/**
 * @author bsgreentea
 */
object PreferenceManager {

    private const val PREFERENCE_NAME = "bikini"

    private fun getPreferences(): SharedPreferences {
        return AppResources.getContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    fun setBoolean(key: String, value: Boolean) {
        getPreferences().edit().putBoolean(key, value).apply()
    }

    fun setString(key: String, value: String) {
        getPreferences().edit().putString(key, value).apply()
    }

    fun setImageCompressionRate(value: SettingsImageCompressionRate) {
        getPreferences().edit()
            .putInt(AppResources.getStringResId(R.string.image_compression_rate), value.rate)
            .apply()
    }

    fun getImageCompressionRate(): SettingsImageCompressionRate {
        val compressionRate = getPreferences().getInt(
            AppResources.getStringResId(R.string.image_compression_rate),
            SettingsImageCompressionRate.NORMAL_QUALITY.rate
        )
        return SettingsImageCompressionRate.fromRate(compressionRate)
    }

    fun getBoolean(key: String) = getPreferences().getBoolean(key, false)
}