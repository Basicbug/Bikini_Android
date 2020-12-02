/*
 * BikiniMapFragment.kt 2020. 11. 1
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.map

import android.os.Bundle
import android.util.ArrayMap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.bikini_android.R
import com.example.bikini_android.app.AppResources
import com.example.bikini_android.databinding.FragmentBikiniMapBinding
import com.example.bikini_android.databinding.ViewFeedMarkerBinding
import com.example.bikini_android.repository.feed.FeedMarker
import com.example.bikini_android.ui.base.BaseMapFragment
import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.map.GoogleMapUtils
import com.example.bikini_android.util.rx.addTo
import com.google.android.gms.maps.GoogleMap
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * @author MyeongKi
 */

class BikiniMapFragment : BaseMapFragment() {
    private lateinit var binding: FragmentBikiniMapBinding
    private val itemEventRelay: Relay<RxAction> = PublishRelay.create()
    private val feedMarkerBindingTable = ArrayMap<FeedMarker, ViewFeedMarkerBinding>()
    private val viewModel: BikiniMapViewModel by lazy {
        ViewModelProvider(this)[BikiniMapViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        DataBindingUtil.inflate<FragmentBikiniMapBinding>(inflater, R.layout.fragment_bikini_map, container, false)
            .also {
                binding = it
                viewModel.init(itemEventRelay, disposables)
            }.root

    override fun onMapReady(googleMap: GoogleMap?) {
        super.onMapReady(googleMap)
        observeMapEvent()
        viewModel.loadFeedMarkers()
    }

    override fun onDestroyView() {
        feedMarkerBindingTable.clear()
        super.onDestroyView()
    }

    private fun observeMapEvent() {
        itemEventRelay
            .ofType(FeedMarkerImageLoadEvent::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { event ->
                addMarker(event.feedMarker)
            }.addTo(disposables)

        itemEventRelay
            .ofType(FeedMarkersLoadEvent::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { event ->
                bindFeedMarkerList(event.feedMarkers)
            }.addTo(disposables)
    }

    private fun addMarker(feedMarker: FeedMarker) {
        feedMarkerBindingTable[feedMarker]?.root?.let {
            map.addMarker(GoogleMapUtils.getFeedMarkerOption(it, feedMarker.position))
        }
    }

    private fun bindFeedMarkerList(feedMarkerList: List<FeedMarker>) {
        for (feedMarker in feedMarkerList) {
            getFeedMarkerBinding().run {
                feedMarkerBindingTable[feedMarker] = this
                apply {
                    viewmodel = FeedMarkerItemViewModel(feedMarker).also {
                        it.itemEventRelay = itemEventRelay
                    }
                    executePendingBindings()
                }
            }
        }
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

    companion object {
        fun newInstance(): BikiniMapFragment {
            return BikiniMapFragment()
        }
    }
}