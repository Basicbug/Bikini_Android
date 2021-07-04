/*
 * SortDefaultCriteriaImpl.kt 2021. 6. 29
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.sort

/**
 * @author MyeongKi
 */
class SortFeedCriteriaProvider : SortCriteriaProvider {
    override fun getCriteria(sortTarget: SortTarget): SortCriteriaOption {
        return when (sortTarget) {
            SortTarget.FEED_DISTANCE -> {
                SortCriteriaOption.SORT_DISTANCE_ASC
            }
            SortTarget.FEED_UPDATE -> {
                SortCriteriaOption.SORT_UPDATE_DESC
            }
            SortTarget.FEED_LIKE -> {
                SortCriteriaOption.SORT_LIKE_COUNT_DESC
            }
            else -> {
                throw IllegalAccessException("invalid sort target")
            }
        }
    }
}