/*
 * ItemViewModel.kt 2020. 11. 16
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.common.item

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.bikini_android.BR
import com.example.bikini_android.util.bus.RxAction
import com.jakewharton.rxrelay2.Relay

/**
 * @author MyeongKi
 */

abstract class ItemViewModel : BaseObservable(),
    BindableLayout {
    @get:Bindable
    var enabled = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.enabled)
        }

    var itemEventRelay: Relay<RxAction>? = null

    open fun onClickItem() = Unit
    open fun onLongClickItem() = Unit
    open fun getItemHashCode(): Int = this.hashCode()
    open fun getContentsHashCode(): Int = this.hashCode()
}