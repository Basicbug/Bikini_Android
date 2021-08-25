/*
 * LikesItemViewModel.kt 2021. 8. 25
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android

import com.example.bikini_android.repository.likes.Likes
import com.example.bikini_android.repository.likes.LikesTargetType
import com.example.bikini_android.ui.likes.LikesItemViewModel
import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.rx.TestSchedulerProvider
import com.example.bikini_android.util.string.EMPTY_STRING
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * @author MyeongKi
 */
class LikesItemViewModelTest {

    private lateinit var viewModel: LikesItemViewModel

    private val itemEventRelay: Relay<RxAction> = PublishRelay.create()

    private val testSchedulerProvider = TestSchedulerProvider()

    @Before
    fun setup() {
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        viewModel = LikesItemViewModel(itemEventRelay, Likes(EMPTY_STRING, LikesTargetType.NONE, false))
    }

    @Test
    fun onLikesClickInvokeEventTest() {
        var isCompleteEventInvoked = false
        itemEventRelay
            .ofType(LikesItemViewModel.EventType::class.java)
            .observeOn(testSchedulerProvider.testScheduler)
            .subscribe {
                isCompleteEventInvoked = when (it) {
                    is LikesItemViewModel.EventType.AddLikes -> {
                        true
                    }
                    else -> false
                }
            }
        viewModel.onClickLikes()
        testSchedulerProvider.testScheduler.triggerActions()
        Assert.assertTrue(isCompleteEventInvoked)
    }
}