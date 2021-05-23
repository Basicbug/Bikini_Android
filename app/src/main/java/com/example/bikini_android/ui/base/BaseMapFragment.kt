/*
 * BaseMapFragment.kt 2021. 3. 17
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.base

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.bikini_android.ui.map.MapLocationChangeEvent
import com.example.bikini_android.ui.map.viewmodel.MapViewModel
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
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.android.schedulers.AndroidSchedulers
import java.lang.ref.WeakReference

/**
 * @author MyeongKi
 */

abstract class BaseMapFragment : BaseFragment(), OnMapReadyCallback {

    protected var mapView: MapView? = null
    protected lateinit var map: WeakReference<GoogleMap>
    private var permissionDenied = false
    protected val fragmentItemEventRelay: Relay<RxAction> = PublishRelay.create()
    protected lateinit var mapViewModel: MapViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        RxActionBus.toObservable(LocationPermissionEvent::class.java).subscribe {
            if (it.isAccept) {
                initMap()
            } else {
                permissionDenied = true
            }
        }.addTo(disposables)
        mapViewModel.itemEventRelay
            .ofType(MapViewModel.MoveToLocationEvent::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { event ->
                moveToLocation(event.latLng, event.zoom)
            }.addTo(disposables)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = WeakReference(googleMap)
        initMap()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mapView?.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        if (permissionDenied) {
            showMissingPermissionError()
            permissionDenied = false
        }
        mapView?.onResume()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onDestroyView() {
        map.get()?.let {
            mapViewModel.saveLastState(it)
            it.clear()
        }
        map.clear()
        mapView?.onDestroy()
        mapView = null
        super.onDestroyView()
    }

    private fun showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog.newInstance(true)
            .show(requireActivity().supportFragmentManager, "dialog")
    }

    private fun initMap() {
        if (setMyLocationEnable()) {
            mapViewModel.initMap()
            map.get()?.let {
                it.setOnCameraIdleListener {
                    fragmentItemEventRelay.accept(
                        MapLocationChangeEvent(
                            it.cameraPosition.target,
                            GoogleMapUtils.getVisibleRadius(it.projection.visibleRegion)
                        )
                    )
                }
            }
        }
    }

    private fun setMyLocationEnable(): Boolean {
        if (!(::map.isInitialized)) return true
        if (LocationUtils.checkLocationPermission()) {
            map.get()?.isMyLocationEnabled = true
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
        map.get()?.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(latLng.latitude, latLng.longitude),
                zoomSize
            )
        )
    }

    companion object {
        const val DEFAULT_ZOOM_SIZE = 16.0f
    }
}
