package com.example.bikini_android.ui.feeds.adapter

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.bikini_android.BR
import com.example.bikini_android.R

/**
 * @author bsgreentea
 */
class FeedItemViewModel : BaseObservable() {

    @get: Bindable
    var userId = ""
        set(value){
            field = value
            notifyPropertyChanged(BR.userId)
        }

    fun getLayoutRes(): Int = R.layout.item_feed
}