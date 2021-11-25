/*
 * ItemViewModel.kt 2021. 12. 2
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.core.ui.item

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.basicbug.core.util.bus.RxAction
import com.jakewharton.rxrelay2.Relay
import com.basicbug.core.BR

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