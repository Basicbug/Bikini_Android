/*
 * MapViewModel.kt 2021. 3. 17
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.map.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.example.bikini_android.repository.feed.LocationInfo
import com.example.bikini_android.repository.feed.convertLocationInfo
import com.example.bikini_android.ui.base.BaseMapFragment
import com.example.bikini_android.ui.base.BaseViewModel
import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.map.LocationUtils
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay

/**
 * @author MyeongKi
 */
abstract class MapViewModel(private val handle: SavedStateHandle) : BaseViewModel() {
    val itemEventRelay: Relay<RxAction> = PublishRelay.create()
    var locationFocused: LocationInfo? = handle.get<LocationInfo>(KEY_LOCATION_FOCUSED)
    var zoom: Float = handle.get<Float>(KEY_ZOOM) ?: BaseMapFragment.DEFAULT_ZOOM_SIZE

    fun initMap() {
        if (locationFocused == null) {
            LocationUtils.addOnCompleteCurrentLocationListener {
                it?.let {
                    locationFocused = LocationInfo(it.latitude, it.longitude)
                    invokeLocationFocused()
                }
            }
        }
        invokeLocationFocused()
    }

    private fun invokeLocationFocused() {
        locationFocused?.let {
            itemEventRelay.accept(MoveToLocationEvent(LatLng(it.latitude, it.longitude), zoom))
        }
    }

    fun saveLastState(googleMap: GoogleMap) {
        googleMap.cameraPosition.target?.let {
            locationFocused = it.convertLocationInfo()
        }
        zoom = googleMap.cameraPosition.zoom
    }

    override fun saveState() {
        handle[KEY_ZOOM] = zoom
        handle[KEY_LOCATION_FOCUSED] = locationFocused
    }

    class MoveToLocationEvent(val latLng: LatLng, val zoom: Float) : RxAction

    companion object {
        private const val KEY_ZOOM = "keyZoom"
        private const val KEY_LOCATION_FOCUSED = "keyLocationFocused"
    }
}