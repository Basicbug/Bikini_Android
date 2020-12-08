/*
 * FeedItemViewModel.kt 2020. 11. 17
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.feeds

import androidx.databinding.Bindable
import com.example.bikini_android.BR
import com.example.bikini_android.repository.feed.Feed
import com.example.bikini_android.ui.common.item.ItemViewModel
import java.util.*

/**
 * @author MyeongKi
 */

abstract class FeedItemViewModel(feed: Feed) : ItemViewModel() {

    @get: Bindable
    var userId = feed.userId
        set(value) {
            field = value
            notifyPropertyChanged(BR.userId)
        }
    @get: Bindable
    var imageUri = feed.imageUrl
        set(value) {
            field = value
            notifyPropertyChanged(BR.imageUri)
        }
    var feedNumOfUser = feed.feedNumOfUser
}