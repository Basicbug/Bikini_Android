package com.example.bikini_android.ui.feeds.adapter

import androidx.databinding.Bindable
import com.example.bikini_android.BR
import com.example.bikini_android.R
import com.example.bikini_android.ui.common.ItemViewModel

/**
 * @author bsgreentea
 */
class FeedItemViewModel : ItemViewModel() {

    @get: Bindable
    var userId = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.userId)
        }

    override fun getLayoutRes(): Int = R.layout.item_feed
}