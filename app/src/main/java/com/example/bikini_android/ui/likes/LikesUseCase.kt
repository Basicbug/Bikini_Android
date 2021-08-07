/*
 * LikeItUseCase.kt 2021. 8. 7
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.likes

import com.example.bikini_android.manager.likes.LikesCacheManager
import com.example.bikini_android.repository.likes.Likes
import com.example.bikini_android.repository.likes.LikesRepository
import com.example.bikini_android.repository.likes.LikesRepositoryInjector
import com.example.bikini_android.util.rx.addTo
import io.reactivex.disposables.CompositeDisposable

/**
 * @author MyeongKi
 */
class LikesUseCase(
    private val disposables: CompositeDisposable,
) {

    fun execute(event: LikesItemViewModel.EventType) {
        when (event) {
            is LikesItemViewModel.EventType.AddLikes -> {
                addLikes(event.item)
            }
            is LikesItemViewModel.EventType.RemoveLikes -> {
                removeLikes(event.item)
            }
        }
    }

    private fun addLikes(item: LikesItemViewModel) {
        getLikesRepository(item.targetType)
            .addLikes(item.targetId)
            .subscribe { result->
                result?.let {
                    LikesCacheManager.cacheLikes(result)
                    item.liked = result.liked
                }
            }
            .addTo(disposables)
    }

    private fun removeLikes(item: LikesItemViewModel) {
        getLikesRepository(item.targetType)
            .removeLikes(item.targetId)
            .subscribe { result->
                result?.let {
                    LikesCacheManager.cacheLikes(result)
                    item.liked = result.liked
                }
            }
            .addTo(disposables)
    }

    private fun getLikesRepository(targetType: Likes.TargetType): LikesRepository {
        return LikesRepositoryInjector.getLikesRepository(targetType)
    }
}