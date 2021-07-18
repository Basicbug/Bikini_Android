/*
 * Feeds.kt 2021. 7. 18
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.feeds

import android.os.Parcelable
import androidx.annotation.Keep
import com.example.bikini_android.repository.feed.Feed
import kotlinx.parcelize.Parcelize

/**
 * @author MyeongKi
 */
@Parcelize
@Keep
data class Feeds(val feedList: List<Feed>) : Parcelable