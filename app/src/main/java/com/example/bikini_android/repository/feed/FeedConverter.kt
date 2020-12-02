/*
 * FeedConverter.kt 2020. 12. 3
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.repository.feed

/**
 * @author MyeongKi
 */

object FeedConverter {
    fun convertToFeedMarker(feed: Feed): FeedMarker {
        return FeedMarker(feed.userId, feed.position, feed.imageUrl)
    }
}