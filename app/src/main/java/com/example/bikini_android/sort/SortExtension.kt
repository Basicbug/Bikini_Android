/*
 * SortExtension.kt 2021. 7. 4
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.sort

/**
 * @author MyeongKi
 */

inline fun <T, R : Comparable<R>> Iterable<T>.sortedBy(
    crossinline selector: (T) -> R?,
    isAsc: Boolean
): List<T> {
    return if (isAsc) {
        sortedWith(compareBy(selector))
    } else {
        sortedWith(compareByDescending(selector))
    }
}