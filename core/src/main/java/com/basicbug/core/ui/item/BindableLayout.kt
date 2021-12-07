/*
 * BindableLayout.kt 2021. 12. 2
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.core.ui.item

import androidx.annotation.LayoutRes

/**
 * @author MyeongKi
 */

interface BindableLayout {
    @LayoutRes
    fun getLayoutRes(): Int
}