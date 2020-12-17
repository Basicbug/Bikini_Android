/*
 * FeedGridItemViewModel.kt 2020. 12. 8
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.feeds

import com.example.bikini_android.R
import com.example.bikini_android.repository.feed.Feed

/**
 * @author bsgreentea
 */
class FeedGridItemViewModel(feed: Feed) : FeedItemViewModel(feed) {
    override fun getLayoutRes(): Int = R.layout.item_feed_grid
}