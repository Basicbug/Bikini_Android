/*
 * CacheItemViewModel.kt 2021. 12. 2
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.core.ui.item

/**
 * @author MyeongKi
 */
abstract class CacheItemViewModel : ItemViewModel() {
    abstract fun synchronize()
}