/*
 * SettingsViewModel.kt 2021. 6. 13
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.settings

import com.basicbug.core.ui.base.BaseViewModel
import com.basicbug.core.ui.item.ItemViewModel

/**
 * @author MyeongKi
 */
abstract class SettingsViewModel : BaseViewModel() {
    abstract fun getSettingsItemViewModels(): List<ItemViewModel>
}