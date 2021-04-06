/*
 * FeedGridItemViewModel.kt 2020. 12. 8
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.feeds

import com.example.bikini_android.R
import com.example.bikini_android.repository.feed.Feed
import com.example.bikini_android.util.bus.RxAction

/**
 * @author bsgreentea
 */
class FeedGridItemViewModel(feed: Feed) : FeedItemViewModel(feed) {
    fun onClickImage() {
        itemEventRelay?.accept(ImageClickEvent(feed))
    }

    override fun getLayoutRes(): Int = R.layout.view_feed_grid_item

    class ImageClickEvent(val feed: Feed) : RxAction
}
