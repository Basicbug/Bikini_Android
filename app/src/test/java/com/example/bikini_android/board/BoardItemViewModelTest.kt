/*
 * BoardItemViewModelTest.kt 2021. 9. 2
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.board

import com.example.bikini_android.ui.board.BoardItemViewModel
import com.basicbug.core.util.bus.RxAction
import com.basicbug.core.rx.TestSchedulerProvider
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
class BoardItemViewModelTest {

    private lateinit var viewModel: BoardItemViewModel

    private val itemEventRelay: Relay<RxAction> = PublishRelay.create()

    private val testSchedulerProvider = TestSchedulerProvider()

    @Before
    fun setup() {
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        viewModel = BoardItemViewModel(itemEventRelay, null, null)
    }

    @Test
    fun eventTest() {
        var isCompleteSelectImageEventInvoked = false
        var isCompletePostFeedEventInvoked = false
        itemEventRelay
            .ofType(BoardItemViewModel.EventType::class.java)
            .observeOn(testSchedulerProvider.testScheduler)
            .subscribe {
                when (it) {
                    BoardItemViewModel.EventType.NAVIGATE_SELECT_IMAGE_METHOD -> {
                        isCompleteSelectImageEventInvoked = true
                    }
                    BoardItemViewModel.EventType.POST_FEED -> {
                        isCompletePostFeedEventInvoked = true
                    }
                }
            }
        viewModel.navigateToSelectImageMethod()
        viewModel.postFeed()

        testSchedulerProvider.testScheduler.triggerActions()
        Assert.assertTrue(isCompleteSelectImageEventInvoked && isCompletePostFeedEventInvoked)
    }
}