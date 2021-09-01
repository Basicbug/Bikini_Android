/*
 * LikeBindingAdapter.kt 2021. 8. 7
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
object LikeBindingAdapter {
    @JvmStatic
    @BindingAdapter("isLike")
    fun isLike(view: View, isLike: Boolean) {
        view.isSelected = isLike
    }
}