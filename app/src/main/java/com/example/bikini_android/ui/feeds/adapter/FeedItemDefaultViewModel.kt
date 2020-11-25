package com.example.bikini_android.ui.feeds.adapter

import androidx.databinding.Bindable
import com.example.bikini_android.BR
import com.example.bikini_android.R

/**
 * @author bsgreentea
 */
open class FeedItemDefaultViewModel : FeedItemViewModel() {

    @get: Bindable
    var imageUri = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.imageUri)
        }

    @get: Bindable
    var content = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.content)
        }

    override fun getLayoutRes(): Int = R.layout.item_feed
}