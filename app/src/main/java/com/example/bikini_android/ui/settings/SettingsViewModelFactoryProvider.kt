/*
 * SettingsViewModelFactoryProvider.kt 2021. 6. 13
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bikini_android.ui.settings.image.SettingsImageCompressionViewModel
import com.example.bikini_android.ui.settings.image.SettingsImageViewModel

/**
 * @author MyeongKi
 */
class SettingsViewModelFactoryProvider : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainSettingsViewModel::class.java) -> {
                MainSettingsViewModel() as T
            }
            modelClass.isAssignableFrom(SettingsImageViewModel::class.java) -> {
                SettingsImageViewModel() as T
            }
            modelClass.isAssignableFrom(SettingsImageCompressionViewModel::class.java) -> {
                SettingsImageCompressionViewModel() as T
            }
            modelClass.isAssignableFrom(SettingsFlavorOnlyViewModel::class.java) -> {
                SettingsFlavorOnlyViewModel() as T
            }
            else -> {
                throw IllegalArgumentException()
            }
        }
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun getSettingsViewModelClazz(settingsType: SettingsType): Class<SettingsViewModel> {
            return when (settingsType) {
                SettingsType.MAIN -> {
                    MainSettingsViewModel::class.java as Class<SettingsViewModel>
                }
                SettingsType.IMAGE -> {
                    SettingsImageViewModel::class.java as Class<SettingsViewModel>
                }
                SettingsType.IMAGE_COMPRESSION -> {
                    SettingsImageCompressionViewModel::class.java as Class<SettingsViewModel>
                }
                SettingsType.FLAVOR_ONLY -> {
                    SettingsFlavorOnlyViewModel::class.java as Class<SettingsViewModel>
                }
            }
        }
    }
}