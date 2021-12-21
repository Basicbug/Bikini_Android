/*
 * BaseViewModel.kt 2021. 12. 2
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.core.ui.base

import androidx.lifecycle.ViewModel

/**
 * @author MyeongKi
 */

abstract class BaseViewModel : ViewModel() {
    open fun saveState() = Unit
}