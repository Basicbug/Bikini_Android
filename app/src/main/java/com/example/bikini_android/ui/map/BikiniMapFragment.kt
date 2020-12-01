/*
 * BikiniMapFragment.kt 2020. 11. 1
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.map

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.ArrayMap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.Pools
import androidx.databinding.DataBindingUtil
import com.example.bikini_android.R
import com.example.bikini_android.app.AppResources
import com.example.bikini_android.databinding.FragmentBikiniMapBinding
import com.example.bikini_android.databinding.ViewFeedMarkerBinding
import com.example.bikini_android.repository.feed.FeedMarker
import com.example.bikini_android.ui.base.BaseMapFragment
import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.bus.event.FeedMarkerImageLoadEvent
import com.example.bikini_android.util.rx.addTo
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * @author MyeongKi
 */

class BikiniMapFragment : BaseMapFragment() {
    private lateinit var binding: FragmentBikiniMapBinding
    private val feedMarkerBindingTable = ArrayMap<String, ViewFeedMarkerBinding>()
    private val feedMarkerBindingRecyclePool = Pools.SynchronizedPool<ViewFeedMarkerBinding>(FEED_MARKER_COUNT)
    private val itemEventRelay: Relay<RxAction> = PublishRelay.create()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        DataBindingUtil.inflate<FragmentBikiniMapBinding>(inflater, R.layout.fragment_bikini_map, container, false)
            .also {
                binding = it
            }.root

    override fun onMapReady(googleMap: GoogleMap?) {
        super.onMapReady(googleMap)
        observeMapEvent()
    }

    private fun loadTestFeedMarker(): List<FeedMarker> {
        return mutableListOf<FeedMarker>().apply {
            add(
                FeedMarker(
                    "1",
                    LatLng(37.363188, 127.107497),
                    "https://homepages.cae.wisc.edu/~ece533/images/airplane.png"
                )
            )
            add(
                FeedMarker(
                    "2",
                    LatLng(37.362424, 127.106644),
                    "https://homepages.cae.wisc.edu/~ece533/images/arctichare.png"
                )
            )
            add(
                FeedMarker(
                    "3",
                    LatLng(37.361290, 127.107213),
                    "https://homepages.cae.wisc.edu/~ece533/images/baboon.png"
                )
            )
            add(
                FeedMarker(
                    "4",
                    LatLng(37.361025, 127.107824),
                    "https://homepages.cae.wisc.edu/~ece533/images/boat.png"
                )
            )
        }
    }

    private fun bindFeedMarker(feedMarkerList: List<FeedMarker>) {
        for (feedMarker in feedMarkerList) {
            var binding = feedMarkerBindingRecyclePool.acquire()
            if (binding == null) {
                binding = getFeedMarkerBinding()
                feedMarkerBindingTable[feedMarker.id] = binding
            }
            binding.apply {
                viewmodel = FeedMarkerItemViewModel(feedMarker).also {
                    it.itemEventRelay = itemEventRelay
                }
                executePendingBindings()
            }
        }
    }

    private fun getFeedMarkerOption(feedMarker: FeedMarker): MarkerOptions {
        return MarkerOptions()
            .position(feedMarker.position)
            .icon(BitmapDescriptorFactory.fromBitmap(convertBitmap(feedMarker)))
    }

    private fun getFeedMarkerBinding(): ViewFeedMarkerBinding {
        return DataBindingUtil.inflate<ViewFeedMarkerBinding>(
            LayoutInflater.from(requireContext()),
            R.layout.view_feed_marker,
            (view?.parent as ViewGroup),
            false
        ).apply {
            root.run {
                val displayMetrics = AppResources.getContext().resources.displayMetrics
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
                layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
            }
        }
    }

    private fun observeMapEvent() {
        //더미 구독 후
        bindFeedMarker(loadTestFeedMarker())
        itemEventRelay
            .ofType(FeedMarkerImageLoadEvent::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { event ->
                map.addMarker(
                    getFeedMarkerOption(event.feedMarker)
                )
            }.addTo(disposables)
    }

    private fun convertBitmap(feedMarker: FeedMarker): Bitmap? {
        feedMarkerBindingTable[feedMarker.id]?.root?.let { feedMarkerView ->
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

    companion object {
        private const val FEED_MARKER_COUNT = 10
        fun newInstance(): BikiniMapFragment {
            return BikiniMapFragment()
        }
    }
}