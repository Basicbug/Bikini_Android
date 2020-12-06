/*
 * FeedMarkerItemViewModel.kt 2020. 11. 29
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.map

import androidx.databinding.Bindable
import com.example.bikini_android.R
import com.example.bikini_android.repository.feed.Feed
import com.example.bikini_android.ui.common.item.ItemViewModel

/**
 * @author MyeongKi
 */

class FeedMarkerItemViewModel(val feed: Feed) : ItemViewModel() {
    @get:Bindable
    var imageUrl = feed.imageUrl

    fun getImageLoadEvent() {
        itemEventRelay?.accept(FeedMarkerImageLoadEvent(feed))
    }

    override fun getLayoutRes(): Int {
        return R.layout.view_feed_marker
    }

}