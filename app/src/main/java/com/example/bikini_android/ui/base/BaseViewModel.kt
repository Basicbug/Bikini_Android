/*
 * BaseViewModel.kt 2020. 12. 7
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.base

import androidx.lifecycle.ViewModel

/**
 * @author MyeongKi
 */

abstract class BaseViewModel : ViewModel() {
    open fun saveState() = Unit
}