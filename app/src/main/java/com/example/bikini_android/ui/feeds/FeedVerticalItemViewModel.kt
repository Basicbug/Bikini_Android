package com.example.bikini_android.ui.feeds

import androidx.databinding.Bindable
import androidx.databinding.Observable
import com.basicbug.core.app.AppResources
import com.basicbug.core.string.StringUtils
import com.basicbug.core.util.bus.RxAction
import com.example.bikini_android.BR
import com.example.bikini_android.R
import com.example.bikini_android.repository.feed.Feed
import com.example.bikini_android.repository.feed.getDistanceFromMyLocation
import com.example.bikini_android.ui.likes.LikesItemViewModel
import com.jakewharton.rxrelay2.Relay

/**
 * @author bsgreentea
 */
class FeedVerticalItemViewModel(feed: Feed, itemEventRelay: Relay<RxAction>) :
    FeedItemViewModel(feed, itemEventRelay) {
    val likesItemViewModel = LikesItemViewModel(itemEventRelay, feed.likes)
    var lastNumOfLikes = feed.numOfLikes

    init {
        likesItemViewModel.likedObservable.addOnPropertyChangedCallback(object :
            Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                lastNumOfLikes = if (likesItemViewModel.liked) {
                    lastNumOfLikes + 1
                } else {
                    lastNumOfLikes - 1
                }
                feedInfo = getFeedInfo(lastNumOfLikes, feed.getDistanceFromMyLocation())
            }
        })
    }

    @get: Bindable
    var content = feed.content
        set(value) {
            field = value
            notifyPropertyChanged(BR.content)
        }

    @get: Bindable
    var feedInfo: String = getFeedInfo(feed.numOfLikes, feed.getDistanceFromMyLocation())
        set(value) {
            field = value
            notifyPropertyChanged(BR.feedInfo)
        }

    private fun getFeedInfo(numOfLikes: Int, distance: Float): String {
        return StringUtils.appendString(
            AppResources.getString(R.string.like_count, numOfLikes),
            AppResources.getString(R.string.distance_km, distance)
        )
    }

    fun onClickLocation() {
        itemEventRelay?.accept(LocationClickEvent(feed))
    }

    override fun synchronize() {
        likesItemViewModel.synchronize()
    }

    override fun getLayoutRes(): Int = R.layout.view_feed_item

    class LocationClickEvent(val feed: Feed) : RxAction
}
