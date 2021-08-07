/*
 * RefreshItemViewModel.kt 2021. 8. 7
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.common.item

/**
 * @author MyeongKi
 */
abstract class CacheItemViewModel : ItemViewModel() {
    abstract fun synchronize()
}