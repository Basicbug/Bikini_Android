/*
 * LikesUseCaseTest.kt 2021. 8. 27
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.likes

import com.example.bikini_android.manager.likes.LikesCacheManager
import com.example.bikini_android.repository.likes.Likes
import com.example.bikini_android.repository.likes.LikesRepository
import com.example.bikini_android.repository.likes.LikesTargetType
import com.example.bikini_android.ui.likes.LikesItemViewModel
import com.example.bikini_android.ui.likes.LikesUseCase
import com.example.bikini_android.util.rx.TestSchedulerProvider
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * @author MyeongKi
 */
class LikesUseCaseTest {
    private lateinit var useCase: LikesUseCase

    @Mock
    lateinit var likesRepository: LikesRepository

    @Mock
    lateinit var disposable: CompositeDisposable

    private val testSchedulerProvider = TestSchedulerProvider()

    @Mock
    lateinit var likesItemViewModel: LikesItemViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        useCase = LikesUseCase(disposable, likesRepository, testSchedulerProvider)
    }

    @Test
    fun addLikesTest() {
        Mockito.`when`(likesRepository.addLikes("testId")).thenReturn(
            Single.just(Likes("testId", LikesTargetType.FEED, true))
        )
        Mockito.`when`(likesItemViewModel.targetId).thenReturn(
            "testId"
        )
        useCase.execute(LikesItemViewModel.EventType.AddLikes(likesItemViewModel))
        testSchedulerProvider.testScheduler.triggerActions()
        Assert.assertTrue(LikesCacheManager.isLikes("testId", LikesTargetType.FEED)?.liked ?: false)
    }

    @Test
    fun removeLikesTest() {
        Mockito.`when`(likesRepository.removeLikes("testId")).thenReturn(
            Single.just(Likes("testId", LikesTargetType.FEED, false))
        )
        Mockito.`when`(likesItemViewModel.targetId).thenReturn(
            "testId"
        )

        useCase.execute(LikesItemViewModel.EventType.RemoveLikes(likesItemViewModel))
        testSchedulerProvider.testScheduler.triggerActions()

        Assert.assertFalse(LikesCacheManager.isLikes("testId", LikesTargetType.FEED)?.liked ?: true)
    }
}