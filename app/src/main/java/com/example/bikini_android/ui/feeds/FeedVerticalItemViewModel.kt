package com.example.bikini_android.ui.feeds

import androidx.databinding.Bindable
import com.example.bikini_android.BR
import com.example.bikini_android.R
import com.example.bikini_android.app.AppResources
import com.example.bikini_android.repository.feed.Feed
import com.example.bikini_android.repository.feed.getDistanceFromMyLocation
import com.example.bikini_android.util.bus.RxAction

/**
 * @author bsgreentea
 */
class FeedVerticalItemViewModel(feed: Feed) : FeedItemViewModel(feed) {

    @get: Bindable
    var content = feed.content
        set(value) {
            field = value
            notifyPropertyChanged(BR.content)
        }

    @get: Bindable
    var distance: String =
        AppResources.getString(R.string.distance_km, feed.getDistanceFromMyLocation())
        set(value) {
            field = value
            notifyPropertyChanged(BR.content)
        }

    fun onClickLocation() {
        itemEventRelay?.accept(LocationClickEvent(feed))
    }

    override fun getLayoutRes(): Int = R.layout.view_feed_item

    class LocationClickEvent(val feed: Feed) : RxAction
}
