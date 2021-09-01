package com.example.bikini_android.ui.feeds

import androidx.databinding.Bindable
import com.example.bikini_android.BR
import com.example.bikini_android.R
import com.example.bikini_android.app.AppResources
import com.example.bikini_android.repository.feed.Feed
import com.example.bikini_android.repository.feed.getDistanceFromMyLocation
import com.example.bikini_android.ui.likes.LikesItemViewModel
import com.example.bikini_android.util.bus.RxAction
import com.jakewharton.rxrelay2.Relay

/**
 * @author bsgreentea
 */
class FeedVerticalItemViewModel(feed: Feed, itemEventRelay: Relay<RxAction>) :
    FeedItemViewModel(feed, itemEventRelay) {
    val likesItemViewModel = LikesItemViewModel(itemEventRelay, feed.likes)

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

    override fun synchronize() {
        //FIXME feed 내부 like의 상태와 캐싱된 like의 상태를 비굑하고 이를 이용하여 likeCount를 올릴지 결정
        likesItemViewModel.synchronize()
    }

    override fun getLayoutRes(): Int = R.layout.view_feed_item

    class LocationClickEvent(val feed: Feed) : RxAction
}
