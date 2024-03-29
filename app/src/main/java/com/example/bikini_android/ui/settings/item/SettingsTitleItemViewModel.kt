/*
 * SettingTitleItemViewModel.kt 2021. 4. 4
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.settings.item

import androidx.databinding.Bindable
import com.basicbug.core.ui.item.ItemViewModel
import com.example.bikini_android.BR
import com.example.bikini_android.R

/**
 * @author MyeongKi
 */
class SettingsTitleItemViewModel private constructor(
    title: String
) : ItemViewModel() {
    class Builder(private val title: String) {
        fun build() = SettingsTitleItemViewModel(title)
    }

    @get: Bindable
    var title = title
        set(value) {
            field = value
            notifyPropertyChanged(BR.title)
        }

    override fun getLayoutRes(): Int {
        return R.layout.view_settings_title_item
    }
}