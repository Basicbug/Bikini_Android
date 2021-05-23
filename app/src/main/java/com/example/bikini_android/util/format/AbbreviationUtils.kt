/*
 * AbbreviationUtils.kt 2021. 5. 18
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.util.format

import androidx.annotation.StringRes
import com.example.bikini_android.app.AppResources

/**
 * @author MyeongKi
 */
object AbbreviationUtils {

    fun abbreviateLargeDecimalByFormatRes(
        originalDecimal: Long,
        pivotLargeDecimal: Long,
        @StringRes formatRes: Int
    ): String {
        return if (originalDecimal > pivotLargeDecimal) {
            AppResources.getStringResId(
                formatRes,
                pivotLargeDecimal
            )
        } else {
            originalDecimal.toString()
        }
    }
}
