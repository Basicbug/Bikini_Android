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
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
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

abstract class BaseMapFragment : BaseFragment(), OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {
    protected lateinit var map: GoogleMap
    private var permissionDenied = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RxActionBus.toObservable(LocationPermissionEvent::class.java).subscribe {
            if (it.isAccept) {
                enableMyLocation()
                moveToMyLocation()
            } else {
                permissionDenied = true
            }
        }.addTo(disposables)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (this.childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?)?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap ?: return
        enableMyLocation()
        moveToMyLocation()
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

    private fun enableMyLocation() {
        if (!(::map.isInitialized)) return
        if (checkLocationPermission()) {
            map.isMyLocationEnabled = true
        } else {
            PermissionUtils.requestPermission(
                requireActivity() as AppCompatActivity, LOCATION_PERMISSION_REQUEST_CODE,
                Manifest.permission.ACCESS_FINE_LOCATION, true
            )
        }
    }

    private fun moveToMyLocation() {
        if (checkLocationPermission()) {
            val locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val locationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            locationGps?.let {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), DEFAULT_ZOOM_SIZE))
            }
            val locationWifi = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            locationWifi?.let {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), DEFAULT_ZOOM_SIZE))
            }

        }
    }

    companion object {
        private const val DEFAULT_ZOOM_SIZE = 16.0f
    }
}