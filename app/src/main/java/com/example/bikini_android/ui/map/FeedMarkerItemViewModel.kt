/*
 * FeedMarkerItemViewModel.kt 2020. 11. 29
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.map

import androidx.databinding.Bindable
import com.example.bikini_android.BR
import com.example.bikini_android.R
import com.example.bikini_android.repository.feed.Feed
import com.example.bikini_android.repository.feed.firstImageUrl
import com.basicbug.core.ui.item.ItemViewModel
import com.basicbug.core.util.format.AbbreviationUtils

/**
 * @author MyeongKi
 */

class FeedMarkerItemViewModel(private val feed: Feed) : ItemViewModel() {
    @get:Bindable
    var imageUrl = feed.firstImageUrl()

    @get: Bindable
    var badgeText = ""
        private set(value) {
            field = value
            notifyPropertyChanged(BR.badgeText)
        }

    var badgeCount = 0
        set(value) {
            field = value
            badgeText = AbbreviationUtils.abbreviateLargeDecimalByFormatRes(
                value.toLong(),
                LIMIT_RENDER_BADGE_COUNT,
                R.string.decimal_suffix
            )
            isBadgeVisible = (value > 1)
        }

    @get: Bindable
    var isBadgeVisible = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.badgeVisible)
        }

    fun getImageLoadEvent() {
        itemEventRelay?.accept(FeedMarkerImageLoadEvent(feed))
    }

    override fun getLayoutRes(): Int {
        return R.layout.view_feed_marker
    }

    companion object {
        const val LIMIT_RENDER_BADGE_COUNT = 999L
    }
}