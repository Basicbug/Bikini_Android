/*
 * FeedMarkerItemViewModel.kt 2020. 11. 29
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.map

import androidx.databinding.Bindable
import com.example.bikini_android.R
import com.example.bikini_android.repository.feed.FeedMarker
import com.example.bikini_android.ui.common.ItemViewModel
import com.example.bikini_android.util.bus.event.FeedMarkerImageLoadEvent

/**
 * @author MyeongKi
 */

class FeedMarkerItemViewModel(val feedMarker: FeedMarker) : ItemViewModel() {
    @get:Bindable
    var imageUrl = feedMarker.imageUrl

    fun getImageLoadEvent() {
        itemEventRelay?.accept(FeedMarkerImageLoadEvent(feedMarker))
    }

    override fun getLayoutRes(): Int {
        return R.layout.view_feed_marker
    }

}