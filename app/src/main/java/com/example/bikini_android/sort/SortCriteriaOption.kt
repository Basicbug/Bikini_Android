/*
 * SortCriteriaOption.kt 2021. 6. 29
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.sort

/**
 * @author MyeongKi
 */
enum class SortCriteriaOption(
    val sortType: String,
    @Order val sortOrder: String

) {
    SORT_RECENTLY_UPDATE("RECENTLY_UPDATE", Order.DESC),
    SORT_LIKE_COUNT("LIKE_COUNT", Order.DESC),
    SORT_DISTANCE("DISTANCE", Order.ASC)
    ;

    fun isAscOrder() = sortOrder == Order.ASC
    fun isDescOrder() = sortOrder == Order.DESC

    annotation class Order {
        companion object {
            const val DESC = "DESC"
            const val ASC = "ASC"
            const val NONE = ""
        }
    }
}