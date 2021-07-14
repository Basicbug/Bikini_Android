/*
 * SortDefaultCriteria.kt 2021. 6. 29
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.sort

/**
 * @author MyeongKi
 */
interface SortCriteriaProvider {
    fun getCriteria(sortTarget: SortTarget): SortCriteriaOption
}