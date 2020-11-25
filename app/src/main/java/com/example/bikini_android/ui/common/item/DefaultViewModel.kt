package com.example.bikini_android.ui.common.item

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.bikini_android.BR
import com.example.bikini_android.util.bus.RxAction
import com.jakewharton.rxrelay2.Relay

/**
 * @author bsgreentea
 */
abstract class DefaultViewModel : BaseObservable(),
    BindableLayout {
    @get:Bindable
    var enabled = true
        set(value) {
            field = value
            notifyPropertyChanged(BR.enabled)
        }

    var itemEventRelay: Relay<RxAction>? = null

    fun onClickItem() = Unit
    fun onLongClickItem() = Unit
}