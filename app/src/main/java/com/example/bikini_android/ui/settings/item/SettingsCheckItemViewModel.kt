/*
 * SettingsCheckItemViewModel.kt 2021. 6. 13
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.settings.item

import androidx.databinding.Bindable
import com.example.bikini_android.BR
import com.example.bikini_android.R
import com.basicbug.core.ui.item.ItemViewModel

/**
 * @author MyeongKi
 */
class SettingsCheckItemViewModel private constructor(
    content: String,
    isChecked: Boolean,
    private val onClickAction: (() -> Unit)?
) : ItemViewModel() {
    class Builder(private val content: String) {
        private var onClickAction: (() -> Unit)? = null
        private var isChecked: Boolean = false
        fun setOnClickAction(action: () -> Unit): Builder {
            onClickAction = action
            return this
        }

        fun setChecked(isChecked: Boolean): Builder {
            this.isChecked = isChecked
            return this
        }

        fun build() = SettingsCheckItemViewModel(content, isChecked, onClickAction)
    }

    @get: Bindable
    var content = content
        set(value) {
            field = value
            notifyPropertyChanged(BR.content)
        }

    @get: Bindable
    var isChecked = isChecked
        set(value) {
            field = value
            notifyPropertyChanged(BR.checked)
        }

    override fun onClickItem() {
        super.onClickItem()
        onClickAction?.invoke()
    }

    override fun getLayoutRes(): Int {
        return R.layout.view_settings_check_item
    }
}