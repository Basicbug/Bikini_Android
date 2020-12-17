package com.example.bikini_android.ui.feeds

import androidx.databinding.Bindable
import com.example.bikini_android.BR
import com.example.bikini_android.R
import com.example.bikini_android.repository.feed.Feed
import com.example.bikini_android.ui.feeds.FeedItemViewModel

/**
 * @author bsgreentea
 */
class FeedLinearItemViewModel(feed: Feed) : FeedItemViewModel(feed) {

    @get: Bindable
    var content = feed.content
        set(value) {
            field = value
            notifyPropertyChanged(BR.content)
        }

    override fun getLayoutRes(): Int = R.layout.item_feed
}