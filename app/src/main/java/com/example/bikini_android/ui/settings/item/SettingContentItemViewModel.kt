/*
 * SettingContentItemViewModel.kt 2021. 4. 4
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
class SettingContentItemViewModel private constructor(
    content: String,
    private val onClickAction: (() -> Unit)?
) : ItemViewModel() {
    class Builder(private val content: String) {
        private var onClickAction: (() -> Unit)? = null

        fun setOnClickAction(action: () -> Unit): Builder {
            onClickAction = action
            return this
        }

        fun build() = SettingContentItemViewModel(content, onClickAction)
    }

    @get: Bindable
    var content = content
        set(value) {
            field = value
            notifyPropertyChanged(BR.content)
        }

    fun onClick() {
        onClickAction?.invoke()
    }

    override fun getLayoutRes(): Int {
        return R.layout.view_setting_content_item
    }
}