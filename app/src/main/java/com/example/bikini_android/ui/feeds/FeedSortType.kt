/*
 * FeedSortType.kt 2020. 12. 8
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.feeds

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author MyeongKi
 */
@Parcelize
enum class FeedSortType : Parcelable {
    POPULAR,
    NEAR,
}