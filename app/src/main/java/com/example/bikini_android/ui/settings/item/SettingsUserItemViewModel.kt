/*
 * SettingsUserItemViewModel.kt 2021. 9. 11
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
class SettingsUserItemViewModel private constructor(
    name: String,
    imageUrl: String?,
    private val onClickAction: (() -> Unit)?
) : ItemViewModel() {
    class Builder(private val name: String) {
        private var imageUrl: String? = null
        private var onClickAction: (() -> Unit)? = null

        fun setImageUrl(imageUrl: String): SettingsUserItemViewModel.Builder {
            this.imageUrl = imageUrl
            return this
        }

        fun setOnClickAction(action: () -> Unit): SettingsUserItemViewModel.Builder {
            onClickAction = action
            return this
        }

        fun build() = SettingsUserItemViewModel(name, imageUrl, onClickAction)
    }

    @get: Bindable
    var name = name
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }

    @get: Bindable
    var imageUrl = imageUrl
        set(value) {
            field = value
            notifyPropertyChanged(BR.imageUrl)
        }

    override fun onClickItem() {
        super.onClickItem()
        onClickAction?.invoke()
    }

    override fun getLayoutRes(): Int {
        return R.layout.view_settings_user_item
    }
}