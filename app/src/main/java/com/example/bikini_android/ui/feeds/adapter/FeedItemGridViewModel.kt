package com.example.bikini_android.ui.feeds.adapter

import androidx.databinding.Bindable
import com.example.bikini_android.BR
import com.example.bikini_android.R
import com.example.bikini_android.ui.common.ItemViewModel

/**
 * @author bsgreentea
 */
class FeedItemGridViewModel : ItemViewModel() {

    @get: Bindable
    var imageUri = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.imageUri)
        }

    override fun getLayoutRes(): Int = R.layout.item_feed_grid
}