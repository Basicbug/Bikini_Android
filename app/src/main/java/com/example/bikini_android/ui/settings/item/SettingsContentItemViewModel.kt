/*
 * SettingContentItemViewModel.kt 2021. 4. 4
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
class SettingsContentItemViewModel private constructor(
    content: String,
    private val onClickAction: (() -> Unit)?
) : ItemViewModel() {
    class Builder(private val content: String) {
        private var onClickAction: (() -> Unit)? = null

        fun setOnClickAction(action: () -> Unit): Builder {
            onClickAction = action
            return this
        }

        fun build() = SettingsContentItemViewModel(content, onClickAction)
    }

    @get: Bindable
    var content = content
        set(value) {
            field = value
            notifyPropertyChanged(BR.content)
        }

    override fun onClickItem() {
        super.onClickItem()
        onClickAction?.invoke()
    }

    override fun getLayoutRes(): Int {
        return R.layout.view_settings_content_item
    }
}