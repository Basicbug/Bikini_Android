/*
 * BaseMapFragment.kt 2020. 11. 24
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.base

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.bikini_android.R
import com.example.bikini_android.repository.feed.LocationInfo
import com.example.bikini_android.ui.map.MapLocationChangeEvent
import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.bus.RxActionBus
import com.example.bikini_android.util.bus.event.LocationPermissionEvent
import com.example.bikini_android.util.map.GoogleMapUtils
import com.example.bikini_android.util.map.LocationUtils
import com.example.bikini_android.util.permission.PermissionUtils
import com.example.bikini_android.util.permission.PermissionUtils.LOCATION_PERMISSION_REQUEST_CODE
import com.example.bikini_android.util.rx.addTo
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.jakewharton.rxrelay2.Relay

/**
 * @author MyeongKi
 */

abstract class BaseMapFragment : BaseFragment(), OnMapReadyCallback {
    protected lateinit var map: GoogleMap
    private var permissionDenied = false
    protected var locationFocused: LocationInfo? = null
    private var isMoveToLocation = false
    protected lateinit var itemEventRelay: Relay<RxAction>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        RxActionBus.toObservable(LocationPermissionEvent::class.java).subscribe {
            if (it.isAccept) {
                initMap()
            } else {
                permissionDenied = true
            }
        }.addTo(disposables)

        (this.childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?)?.getMapAsync(
            this
        )
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap ?: return
        initMap()

    }

    override fun onResume() {
        super.onResume()
        if (permissionDenied) {
            showMissingPermissionError()
            permissionDenied = false
        }
    }

    override fun onDestroyView() {
        map.clear()
        super.onDestroyView()
    }

    private fun showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog.newInstance(true)
            .show(requireActivity().supportFragmentManager, "dialog")
    }

    private fun initMap() {
        if (setMyLocationEnable()) {
            if (locationFocused == null) {
                LocationUtils.getCurrentLocation()?.let {
                    locationFocused = LocationInfo(it.latitude, it.longitude)
                }
            }
            if (!isMoveToLocation) {
                locationFocused?.let {
                    moveToLocation(LatLng(it.latitude, it.longitude))
                    isMoveToLocation = true
                }
            }
            map.setOnCameraIdleListener {
                itemEventRelay.accept(
                    MapLocationChangeEvent(
                        map.cameraPosition.target,
                        GoogleMapUtils.getVisibleRadius(map.projection.visibleRegion)
                    )
                )
            }
        }
    }

    private fun setMyLocationEnable(): Boolean {
        if (!(::map.isInitialized)) return true
        if (LocationUtils.checkLocationPermission()) {
            map.isMyLocationEnabled = true
            return true
        } else {
            PermissionUtils.requestPermission(
                requireActivity() as AppCompatActivity, LOCATION_PERMISSION_REQUEST_CODE,
                Manifest.permission.ACCESS_FINE_LOCATION, true
            )
        }
        return false
    }

    private fun moveToLocation(latLng: LatLng, zoomSize: Float = DEFAULT_ZOOM_SIZE) {
        map.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(latLng.latitude, latLng.longitude),
                zoomSize
            )
        )
    }

    companion object {
        private const val DEFAULT_ZOOM_SIZE = 16.0f
    }
}