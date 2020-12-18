/*
 * BaseMapFragment.kt 2020. 11. 24
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.base

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.bikini_android.R
import com.example.bikini_android.util.bus.RxActionBus
import com.example.bikini_android.util.bus.event.LocationPermissionEvent
import com.example.bikini_android.util.permission.PermissionUtils
import com.example.bikini_android.util.permission.PermissionUtils.LOCATION_PERMISSION_REQUEST_CODE
import com.example.bikini_android.util.rx.addTo
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

/**
 * @author MyeongKi
 */

abstract class BaseMapFragment : BaseFragment(), OnMapReadyCallback {
    protected lateinit var map: GoogleMap
    private var permissionDenied = false
    private var isFirstInitMyLocation = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        RxActionBus.toObservable(LocationPermissionEvent::class.java).subscribe {
            if (it.isAccept) {
                initMap()
            } else {
                permissionDenied = true
            }
        }.addTo(disposables)

        (this.childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?)?.getMapAsync(this)
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

    private fun showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog.newInstance(true)
            .show(requireActivity().supportFragmentManager, "dialog")
    }

    private fun checkLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun initMap() {
        if (setMyLocationEnable()) {
            if (!isFirstInitMyLocation) {
                moveToMyLocation()
                isFirstInitMyLocation = true
            }
        }
    }

    private fun setMyLocationEnable(): Boolean {
        if (!(::map.isInitialized)) return true
        if (checkLocationPermission()) {
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

    private fun moveToMyLocation() {
        getCurrentLocation()?.let {
            moveToLocation(LatLng(it.latitude, it.longitude))
        }
    }

    protected fun moveToLocation(latLng: LatLng, zoomSize: Float = DEFAULT_ZOOM_SIZE) {
        map.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(latLng.latitude, latLng.longitude),
                zoomSize
            )
        )
    }

    protected fun getCurrentLocation(): Location? {
        if (checkLocationPermission()) {
            (requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager).run {
                this.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).also { networkLocation ->
                    if (networkLocation != null) {
                        return networkLocation
                    } else {
                        this.getLastKnownLocation(LocationManager.GPS_PROVIDER)?.let { gpsLocation ->
                            return gpsLocation
                        }
                    }
                }
            }
        }
        return null
    }

    companion object {
        private const val DEFAULT_ZOOM_SIZE = 16.0f
    }
}