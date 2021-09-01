/*
 * LikeItUseCase.kt 2021. 8. 7
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.likes

import com.example.bikini_android.manager.likes.LikesCacheManager
import com.example.bikini_android.repository.likes.LikesRepository
import com.example.bikini_android.util.rx.SchedulerProvider
import com.example.bikini_android.util.rx.addTo
import io.reactivex.disposables.CompositeDisposable

/**
 * @author MyeongKi
 */

class LikesUseCase(
    private val disposables: CompositeDisposable,
    private val repository: LikesRepository,
    private val schedulerProvider: SchedulerProvider,
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
        repository
            .addLikes(item.targetId)
            .subscribeOn(schedulerProvider.io())
            .subscribe { result ->
                result?.let {
                    LikesCacheManager.cacheLikes(result)
                    item.liked = result.liked
                }
            }
            .addTo(disposables)
    }

    private fun removeLikes(item: LikesItemViewModel) {
        repository
            .removeLikes(item.targetId)
            .subscribeOn(schedulerProvider.io())
            .subscribe { result ->
                result?.let {
                    LikesCacheManager.cacheLikes(result)
                    item.liked = result.liked
                }
            }
            .addTo(disposables)
    }
}