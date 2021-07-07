/*
 * SettingsViewModel.kt 2021. 6. 13
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.settings

import com.example.bikini_android.ui.base.BaseViewModel
import com.example.bikini_android.ui.common.item.ItemViewModel

/**
 * @author MyeongKi
 */
abstract class SettingsViewModel : BaseViewModel() {
    abstract fun getSettingsItemViewModels(): List<ItemViewModel>
}