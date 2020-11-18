/*
 * BindableItem.kt 2020. 11. 5
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.common.item

import androidx.annotation.LayoutRes

/**
 * @author MyeongKi
 */

interface BindableLayout {
    @LayoutRes
    fun getLayoutRes(): Int
}