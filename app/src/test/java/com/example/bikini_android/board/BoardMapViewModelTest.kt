/*
 * BoardMapViewModelTest.kt 2021. 9. 2
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.board

import androidx.lifecycle.SavedStateHandle
import com.example.bikini_android.ui.board.BoardMapViewModel
import com.example.bikini_android.util.map.LocationUtils
import com.basicbug.core.rx.TestSchedulerProvider
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
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
class BoardMapViewModelTest {

    @Mock
    lateinit var handle: SavedStateHandle

    @Mock
    lateinit var locationUtils: LocationUtils
    private val testSchedulerProvider = TestSchedulerProvider()
    private lateinit var viewModel: BoardMapViewModel
    private val currentPosition = LatLng(37.362450, 127.107829)
    private val validPosition = LatLng(37.362160, 127.107336)
    private val invalidPosition = LatLng(37.374599, 127.106964)
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        viewModel = BoardMapViewModel(handle, locationUtils)
        Mockito.`when`(locationUtils.getDistanceBetween(currentPosition, validPosition)).thenReturn(
            0.122f
        )
        Mockito.`when`(locationUtils.getDistanceBetween(currentPosition, invalidPosition)).thenReturn(
            0.422f
        )

    }

    @Test
    fun initMarkerTest() {
        val clickPosition = LatLng(0.0, 0.0)
        val currentPosition = LatLng(1.1, 1.1)
        Mockito.`when`(handle.get<MarkerOptions>(KEY_LAST_MARKER)).thenReturn(
            MarkerOptions().draggable(true).position(clickPosition)
        )
        val newViewModel = BoardMapViewModel(handle, locationUtils)
        newViewModel.initMarker(currentPosition)
        Assert.assertTrue(newViewModel.marker.position == clickPosition)
    }

    @Test
    fun moveMarkerToClickPositionTest() {
        var isCompleteValidEventInvoked = false
        var isCompleteInvalidEventInvoked = false
        viewModel.itemEventRelay
            .ofType(BoardMapViewModel.EventType::class.java)
            .observeOn(testSchedulerProvider.testScheduler)
            .subscribe {
                when (it) {
                    BoardMapViewModel.EventType.MOVE_MARKER -> {
                        isCompleteValidEventInvoked = true
                    }
                    BoardMapViewModel.EventType.INVALID_POSITION_CLICK -> {
                        isCompleteInvalidEventInvoked = true
                    }
                }
            }
        viewModel.circleCenter = currentPosition
        viewModel.moveMarkerToClickPosition(validPosition)
        viewModel.moveMarkerToClickPosition(invalidPosition)

        testSchedulerProvider.testScheduler.triggerActions()
        Assert.assertTrue(isCompleteValidEventInvoked && isCompleteInvalidEventInvoked && viewModel.marker.position == validPosition)
    }

    @Test
    fun isValidPositionTest() {
        viewModel.circleCenter = currentPosition
        Assert.assertFalse(viewModel.isValidPosition(invalidPosition))
        Assert.assertTrue(viewModel.isValidPosition(validPosition))
    }

    companion object {
        private const val KEY_LAST_MARKER = "keyLastMarker"
    }
}