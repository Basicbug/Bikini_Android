/*
 * ProgressItemViewModel.kt 2021. 4. 18
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.progress

import androidx.databinding.Bindable
import com.example.bikini_android.BR
import com.example.bikini_android.R
import com.example.bikini_android.ui.common.item.ItemViewModel

/**
 * @author MyeongKi
 */
class ProgressItemViewModel : ItemViewModel() {
    @get: Bindable
    var isVisible = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.visible)
        }
    override fun getLayoutRes(): Int {
        return R.layout.view_progress
    }
}