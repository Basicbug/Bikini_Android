/*
 * BoardViewModelTest.kt 2021. 9. 2
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.board

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import com.example.bikini_android.manager.login.LoginManagerProxy
import com.example.bikini_android.network.response.DefaultResponse
import com.example.bikini_android.repository.feed.Feed
import com.example.bikini_android.repository.feed.FeedRepository
import com.example.bikini_android.repository.feed.convertLocationInfo
import com.example.bikini_android.ui.board.BoardItemViewModel
import com.example.bikini_android.ui.board.BoardViewModel
import com.example.bikini_android.util.bus.RxActionBus
import com.example.bikini_android.util.bus.event.ReloadFeedEvent
import com.example.bikini_android.util.file.FileUtils
import com.example.bikini_android.util.rx.TestSchedulerProvider
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
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
class BoardViewModelTest {

    @Mock
    lateinit var handle: SavedStateHandle

    @Mock
    lateinit var feedRepo: FeedRepository

    @Mock
    lateinit var loginManager: LoginManagerProxy

    @Mock
    lateinit var testUri: Uri

    @Mock
    lateinit var fileUtils: FileUtils
    private val testSchedulerProvider = TestSchedulerProvider()
    private lateinit var viewModel: BoardViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        Mockito.`when`(handle.get<Uri>(KEY_URI)).thenReturn(
            testUri
        )
        Mockito.`when`(handle.get<String>(KEY_CONTENT)).thenReturn(
            "test"
        )

        Mockito.`when`(loginManager.userName).thenReturn(
            "test"
        )
        viewModel = BoardViewModel(handle, feedRepo, loginManager, fileUtils, testSchedulerProvider)
    }

    @Test
    fun postFeedValidTest() {
        var isCompleteRefreshEventInvoked = false
        var isCompleteFinishEventInvoked = false
        val postLocation = LatLng(1.0, 1.0)
        val inputFeed = Feed(
            content = "test",
            locationInfo = postLocation.convertLocationInfo(),
            username = loginManager.userName
        )

        Mockito.`when`(fileUtils.getImageMultiParts(listOf(testUri))).thenReturn(
            emptyList()
        )

        Mockito.`when`(feedRepo.addFeedToRemote(inputFeed, fileUtils.getImageMultiParts(listOf(testUri)))).thenReturn(
            Single.just(DefaultResponse())
        )

        viewModel.itemEventRelay
            .ofType(BoardItemViewModel.EventType::class.java)
            .observeOn(testSchedulerProvider.testScheduler)
            .subscribe {
                when (it) {
                    BoardItemViewModel.EventType.FINISH -> {
                        isCompleteFinishEventInvoked = true
                    }
                }
            }
        RxActionBus.toObservable(ReloadFeedEvent::class.java)
            .observeOn(testSchedulerProvider.testScheduler)
            .subscribe {
                isCompleteRefreshEventInvoked = true
            }
        viewModel.postFeed(postLocation)
        Assert.assertTrue(viewModel.progressViewModel.isVisible)
        testSchedulerProvider.testScheduler.triggerActions()
        Assert.assertFalse(viewModel.progressViewModel.isVisible)
        Assert.assertTrue(isCompleteRefreshEventInvoked && isCompleteFinishEventInvoked)
    }

    @Test
    fun setImageUriSelectedTest() {
        Mockito.`when`(testUri.toString()).thenReturn(
            "testUri"
        )
        viewModel.setImageUriSelected(testUri)
        Assert.assertTrue(viewModel.boardItemViewModel.imageUrl == "testUri")
    }

    companion object {
        private const val KEY_CONTENT = "keyContent"
        private const val KEY_URI = "keyUri"
    }
}