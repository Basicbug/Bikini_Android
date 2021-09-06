/*
 * BoardMapFragment.kt 2021. 8. 29
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.board

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.bikini_android.R
import com.example.bikini_android.app.AppResources
import com.example.bikini_android.app.ToastHelper
import com.example.bikini_android.databinding.FragmentBoardMapBinding
import com.example.bikini_android.ui.base.BaseMapFragment
import com.example.bikini_android.util.ktx.autoCleared
import com.example.bikini_android.util.map.LocationUtils
import com.example.bikini_android.util.rx.addTo
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * @author MyeongKi
 */

class BoardMapFragment : BaseMapFragment() {
    private var binding by autoCleared<FragmentBoardMapBinding>()
    private lateinit var boardMapViewModel: BoardMapViewModel
    private var lastMarkerName: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        boardMapViewModel = ViewModelProvider(
            requireActivity(),
            BoardViewModelFactoryProvider(requireActivity(), savedInstanceState)
        )[BoardMapViewModel::class.java]
        mapViewModel = boardMapViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        DataBindingUtil.inflate<FragmentBoardMapBinding>(
            inflater,
            R.layout.fragment_board_map,
            container,
            false
        ).also {
            super.onCreateView(inflater, container, savedInstanceState)
            binding = it
            mapView = it.map.also { view ->
                view.getMapAsync(this)
            }
            observeEvent()
        }.root

    override fun onMapReady(googleMap: GoogleMap?) {
        super.onMapReady(googleMap)
        initMap()
    }

    private fun initMap() {
        map.get()?.let { map ->
            map.uiSettings.isScrollGesturesEnabled = false
            map.uiSettings.isZoomControlsEnabled = false
            map.uiSettings.isZoomGesturesEnabled = false
            map.uiSettings.isMapToolbarEnabled = false

            LocationUtils.getCurrentLatLng()?.let { currentLocation ->
                boardMapViewModel.circleCenter = currentLocation
                boardMapViewModel.initMarker(currentLocation)

                map.addCircle(
                    boardMapViewModel.selectableMarkerCircle
                        .center(currentLocation)
                        .fillColor(AppResources.getResources().getColor(R.color.posca_opa40, null))
                )
                lastMarkerName = map.addMarker(boardMapViewModel.marker)
            }

            map.setOnMapClickListener {
                boardMapViewModel.moveMarkerToClickPosition(it)
            }
            map.setOnMarkerDragListener(boardMapViewModel.markerDragListener)
        }
    }

    private fun observeEvent() {

        boardMapViewModel.itemEventRelay
            .ofType(BoardMapViewModel.EventType::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                when (it) {
                    BoardMapViewModel.EventType.INVALID_POSITION_CLICK -> {
                        ToastHelper.show(R.string.board_invalid_click)
                    }
                    BoardMapViewModel.EventType.MOVE_MARKER -> {
                        refreshMarker()
                    }
                    BoardMapViewModel.EventType.INVALID_POSITION_DRAG -> {
                        refreshMarker()
                        ToastHelper.show(R.string.board_invalid_click)
                    }
                    else -> Unit
                }
            }.addTo(disposables)
    }

    private fun refreshMarker() {
        lastMarkerName?.remove()
        lastMarkerName = map.get()?.addMarker(boardMapViewModel.marker)
    }
}
