/*
 * AbbreviationUtils.kt 2021. 5. 18
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.core.util.format

import androidx.annotation.StringRes
import com.basicbug.core.app.AppResources

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
            AppResources.getString(
                formatRes,
                pivotLargeDecimal
            )
        } else {
            originalDecimal.toString()
        }
    }
}