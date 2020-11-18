package com.example.bikini_android.ui.feeds.adapter

import androidx.databinding.Bindable
import com.example.bikini_android.BR
import com.example.bikini_android.R

/**
 * @author bsgreentea
 */
class FeedItemGridViewModel :FeedItemViewModel() {

    @get: Bindable
    var imageUri = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.imageUri)
        }

    override fun getLayoutRes(): Int = R.layout.item_feed_grid
}