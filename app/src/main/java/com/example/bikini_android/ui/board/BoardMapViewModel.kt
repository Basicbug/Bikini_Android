/*
 * BoardMapViewModel.kt 2021. 8. 29
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.board

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.SavedStateHandle
import com.example.bikini_android.R
import com.example.bikini_android.app.AppResources
import com.example.bikini_android.ui.map.viewmodel.MapViewModel
import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.map.LocationUtils
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

/**
 * @author MyeongKi
 */
class BoardMapViewModel(private val handle: SavedStateHandle) : MapViewModel(handle) {
    var circleCenter: LatLng? = null

    val selectableMarkerCircle: CircleOptions = CircleOptions()
        .radius(SELECTABLE_MARKER_MAX_RADIUS)
        .fillColor(AppResources.getResources().getColor(R.color.posca_opa40, null))
        .strokeColor(Color.TRANSPARENT)

    val marker: MarkerOptions = handle.get<MarkerOptions>(KEY_LAST_MARKER) ?: MarkerOptions().draggable(true)

    val markerDragListener = object : GoogleMap.OnMarkerDragListener {
        override fun onMarkerDragStart(markerDragStart: Marker) = Unit

        override fun onMarkerDragEnd(markerDragEnd: Marker) {
            marker.position(markerDragEnd.position)
        }

        override fun onMarkerDrag(markerDrag: Marker) {
            if (!isValidPosition(markerDrag.position)) {
                itemEventRelay.accept(EventType.INVALID_POSITION_DRAG)
            }
        }
    }

    fun initMarker(latLng: LatLng) {
        if (marker.position == null) {
            marker.position(latLng)
            marker.icon(getIcon())
        }
    }

    private fun getIcon(): BitmapDescriptor {
        val background =
            ResourcesCompat.getDrawable(AppResources.getResources(), R.drawable.ic_baseline_person_pin_circle_24, null)
        background?.let {
            val bitmap =
                Bitmap.createBitmap(
                    AppResources.getResources().getDimensionPixelOffset(R.dimen.board_marker_width),
                    AppResources.getResources().getDimensionPixelOffset(R.dimen.board_marker_height),
                    Bitmap.Config.ARGB_8888
                )
            val canvas = Canvas(bitmap)
            background.setBounds(
                0,
                0,
                AppResources.getResources().getDimensionPixelOffset(R.dimen.board_marker_width),
                AppResources.getResources().getDimensionPixelOffset(R.dimen.board_marker_height)
            )
            DrawableCompat.setTint(background, AppResources.getResources().getColor(R.color.purple_700, null))
            background.draw(canvas)
            return BitmapDescriptorFactory.fromBitmap(bitmap)
        }
        return BitmapDescriptorFactory.defaultMarker()
    }

    fun moveMarkerToClickPosition(latLng: LatLng) {
        if (isValidPosition(latLng)) {
            marker.position(latLng)
            itemEventRelay.accept(EventType.MOVE_MARKER)
        } else {
            itemEventRelay.accept(EventType.INVALID_POSITION_CLICK)
        }
    }

    override fun saveState() {
        super.saveState()
        handle[KEY_LAST_MARKER] = marker
    }

    fun isValidPosition(latLng: LatLng): Boolean {
        return LocationUtils.getDistanceBetween(circleCenter, latLng) * 1000 < SELECTABLE_MARKER_MAX_RADIUS
    }

    enum class EventType : RxAction {
        MOVE_MARKER, INVALID_POSITION_CLICK, INVALID_POSITION_DRAG;
    }

    companion object {
        private const val SELECTABLE_MARKER_MAX_RADIUS = 200.0
        private const val KEY_LAST_MARKER = "keyLastMarker"
    }
}