package com.example.bikini_android.ui.feeds

import androidx.databinding.Bindable
import com.example.bikini_android.BR
import com.example.bikini_android.R
import com.example.bikini_android.repository.feed.Feed
import com.example.bikini_android.ui.feeds.FeedItemViewModel
import com.example.bikini_android.util.bus.RxAction

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

    fun onClickLocation() {
        itemEventRelay?.accept(LocationClickEvent(feed))
    }

    override fun getLayoutRes(): Int = R.layout.item_feed

    class LocationClickEvent(val feed: Feed) : RxAction
}