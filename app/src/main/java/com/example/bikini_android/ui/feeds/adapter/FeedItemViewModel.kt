/*
 * FeedItemViewModel.kt 2020. 11. 17
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.feeds.adapter

import androidx.databinding.Bindable
import com.example.bikini_android.BR
import com.example.bikini_android.ui.common.item.ItemViewModel

/**
 * @author MyeongKi
 */

abstract class FeedItemViewModel :ItemViewModel(){
    @get: Bindable
    var userId = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.userId)
        }
}