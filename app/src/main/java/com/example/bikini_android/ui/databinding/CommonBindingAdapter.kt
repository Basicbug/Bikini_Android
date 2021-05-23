/*
 * CommonBindingAdapter.kt 2021. 4. 18
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.databinding

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * @author MyeongKi
 */
@BindingAdapter("isVisible")
fun View.isVisible(isVisible: Boolean) {
    visibility = if (isVisible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}
