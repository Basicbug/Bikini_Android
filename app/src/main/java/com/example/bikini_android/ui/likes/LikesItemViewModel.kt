/*
 * LikeItemViewModel.kt 2021. 8. 7
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.likes

import androidx.databinding.Bindable
import com.example.bikini_android.BR
import com.example.bikini_android.R
import com.example.bikini_android.manager.likes.LikesCacheManager
import com.example.bikini_android.repository.likes.Likes
import com.example.bikini_android.repository.likes.LikesTargetType
import com.example.bikini_android.ui.common.item.CacheItemViewModel
import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.string.EMPTY_STRING
import com.jakewharton.rxrelay2.Relay

/**
 * @author MyeongKi
 */
class LikesItemViewModel(
    itemEventRelay: Relay<RxAction>,
    likes: Likes?
) : CacheItemViewModel() {
    val targetId: String = likes?.targetId ?: EMPTY_STRING

    @LikesTargetType
    val targetType: String = likes?.targetType ?: LikesTargetType.NONE

    init {
        this.itemEventRelay = itemEventRelay
    }

    @get: Bindable
    var liked: Boolean = likes?.liked ?: false
        set(value) {
            field = value
            notifyPropertyChanged(BR.liked)
        }

    fun onClickLikes() {
        if (liked) {
            itemEventRelay?.accept(EventType.RemoveLikes(this))
        } else {
            itemEventRelay?.accept(EventType.AddLikes(this))
        }
    }

    override fun synchronize() {
        LikesCacheManager.isLikes(targetId, targetType)?.let {
            liked = it.liked
        }
    }

    override fun getLayoutRes(): Int {
        return R.layout.view_likes_item
    }

    sealed class EventType(val item: LikesItemViewModel) : RxAction {
        class AddLikes(likes: LikesItemViewModel) : EventType(likes)
        class RemoveLikes(likes: LikesItemViewModel) : EventType(likes)
    }
}