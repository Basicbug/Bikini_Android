/*
 * SortFeedExecutor.kt 2021. 7. 4
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.sort

import com.example.bikini_android.repository.feed.Feed
import com.example.bikini_android.repository.feed.getDistanceFromMyLocation

/**
 * @author MyeongKi
 */
object SortFeedExecutor {
    fun execute(feed: List<Feed>, sortCriteriaOption: SortCriteriaOption): List<Feed> {
        return when (sortCriteriaOption.sortType) {
            SortType.DISTANCE -> {
                feed.sortedBy({ it.getDistanceFromMyLocation() }, sortCriteriaOption.isAscOrder())
            }
            SortType.LIKE -> {
                throw IllegalAccessException("Not yet supported")
            }
            SortType.UPDATE -> {
                throw IllegalAccessException("Not yet supported")
            }
            else -> {
                throw IllegalAccessException("invalid sort option")
            }
        }
    }
}
