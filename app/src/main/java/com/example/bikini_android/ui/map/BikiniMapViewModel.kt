/*
 * BikiniMapViewModel.kt 2020. 12. 2
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.map

import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.ArrayMap
import androidx.core.util.Pools
import androidx.lifecycle.ViewModel
import com.example.bikini_android.databinding.ViewFeedMarkerBinding
import com.example.bikini_android.repository.feed.FeedMarker
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions

/**
 * @author MyeongKi
 */

class BikiniMapViewModel : ViewModel() {
    internal val feedMarkerBindingTable = ArrayMap<FeedMarker, ViewFeedMarkerBinding>()
    internal val feedMarkerBindingRecyclePool = Pools.SynchronizedPool<ViewFeedMarkerBinding>(FEED_MARKER_COUNT)

    private fun convertBitmap(feedMarker: FeedMarker): Bitmap? {
        feedMarkerBindingTable[feedMarker]?.root?.let { feedMarkerView ->
            return Bitmap.createBitmap(
                feedMarkerView.measuredWidth,
                feedMarkerView.measuredHeight,
                Bitmap.Config.ARGB_8888
            ).also { bitmap ->
                Canvas(bitmap).run {
                    feedMarkerView.draw(this)
                }
            }
        }
        return null
    }

    fun getFeedMarkerOption(feedMarker: FeedMarker): MarkerOptions {
        return MarkerOptions()
            .position(feedMarker.position)
            .icon(BitmapDescriptorFactory.fromBitmap(convertBitmap(feedMarker)))
    }

    fun clearFeedMarkerBindingRecyclePool(){
        while (true) {
            if (feedMarkerBindingRecyclePool.acquire() == null)
                break
        }
    }
    override fun onCleared() {
        feedMarkerBindingTable.clear()
        clearFeedMarkerBindingRecyclePool()
        super.onCleared()
    }

    companion object {
        private const val FEED_MARKER_COUNT = 10
    }
}