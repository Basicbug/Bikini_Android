/*
 * FeedItemViewModel.kt 2020. 12. 8
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.feeds

import androidx.databinding.Bindable
import com.example.bikini_android.BR
import com.example.bikini_android.repository.feed.Feed
import com.example.bikini_android.repository.feed.firstImageUrl
import com.example.bikini_android.ui.common.item.CacheItemViewModel
import com.example.bikini_android.util.bus.RxAction
import com.jakewharton.rxrelay2.Relay
import java.util.Objects

/**
 * @author MyeongKi
 */

abstract class FeedItemViewModel(
    protected val feed: Feed,
    itemEventRelay: Relay<RxAction>
) : CacheItemViewModel() {
    init {
        this.itemEventRelay = itemEventRelay
    }

    @get: Bindable
    var userId = feed.username
        set(value) {
            field = value
            notifyPropertyChanged(BR.userId)
        }

    @get: Bindable
    var imageUri = feed.firstImageUrl()
        set(value) {
            field = value
            notifyPropertyChanged(BR.imageUri)
        }
    var feedNumOfUser = feed.feedNumOfUser

    override fun getItemHashCode(): Int {
        return Objects.hash(feed.feedId)
    }

    override fun getContentsHashCode(): Int {
        return feed.hashCode()
    }
}
