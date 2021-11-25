/*
 * ProgressItemViewModel.kt 2021. 12. 2
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.core.ui.progress

import androidx.databinding.Bindable
import com.basicbug.core.R
import com.basicbug.core.BR
import com.basicbug.core.ui.item.ItemViewModel

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