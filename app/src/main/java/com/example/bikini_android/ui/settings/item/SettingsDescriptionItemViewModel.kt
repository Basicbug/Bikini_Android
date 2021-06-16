/*
 * SettingsDescriptionItemViewModel.kt 2021. 6. 13
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.settings.item

import androidx.databinding.Bindable
import com.example.bikini_android.BR
import com.example.bikini_android.R
import com.example.bikini_android.ui.common.item.ItemViewModel

/**
 * @author MyeongKi
 */
class SettingsDescriptionItemViewModel private constructor(
    description: String,
) : ItemViewModel() {
    class Builder(private val description: String) {
        fun build() = SettingsDescriptionItemViewModel(description)
    }

    @get: Bindable
    var description = description
        set(value) {
            field = value
            notifyPropertyChanged(BR.description)
        }

    override fun getLayoutRes(): Int {
        return R.layout.view_settings_description_item
    }
}