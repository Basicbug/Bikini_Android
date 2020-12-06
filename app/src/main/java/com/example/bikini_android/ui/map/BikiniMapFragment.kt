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
import com.example.bikini_android.repository.feed.Feed
import com.example.bikini_android.ui.base.BaseMapFragment
import com.example.bikini_android.ui.feeds.FeedsViewModel
import com.example.bikini_android.ui.feeds.FeedsViewModelFactory
import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.map.GoogleMapUtils
import com.example.bikini_android.util.rx.addTo
import com.google.android.gms.maps.GoogleMap
import com.jakewharton.rxrelay2.Relay
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * @author MyeongKi
 */

class BikiniMapFragment : BaseMapFragment() {
    private lateinit var binding: FragmentBikiniMapBinding
    private val viewModel: FeedsViewModel by lazy {
        ViewModelProvider(requireActivity())[FeedsViewModel::class.java]
    }
    private val itemEventRelay: Relay<RxAction> by lazy {
        viewModel.itemEventRelay
    }

    private val feedMarkerBindingTable = ArrayMap<Feed, ViewFeedMarkerBinding>()

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
                addMarker(event.feed)
            }.addTo(disposables)

        itemEventRelay
            .ofType(FeedsLoadEvent::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { event ->
                bindFeedMarkerList(event.feeds)
            }.addTo(disposables)
    }

    private fun addMarker(feed: Feed) {
        feedMarkerBindingTable[feed]?.root?.let { view ->
            feed.position?.let { position ->
                map.addMarker(GoogleMapUtils.getFeedMarkerOption(view, position))
            }
        }
    }

    private fun bindFeedMarkerList(feeds: List<Feed>) {
        for (feed in feeds) {
            getFeedMarkerBinding().run {
                feedMarkerBindingTable[feed] = this
                apply {
                    viewmodel = FeedMarkerItemViewModel(feed).also {
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