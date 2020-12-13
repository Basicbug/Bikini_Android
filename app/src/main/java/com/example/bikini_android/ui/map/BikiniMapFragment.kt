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
import androidx.navigation.fragment.findNavController
import com.example.bikini_android.R
import com.example.bikini_android.app.AppResources
import com.example.bikini_android.databinding.FragmentBikiniMapBinding
import com.example.bikini_android.databinding.ViewFeedMarkerBinding
import com.example.bikini_android.repository.feed.Feed
import com.example.bikini_android.ui.base.BaseMapFragment
import com.example.bikini_android.ui.common.RecyclerViewLayoutType
import com.example.bikini_android.ui.feeds.FeedSortType
import com.example.bikini_android.ui.feeds.FeedsFragment
import com.example.bikini_android.ui.feeds.FeedsType
import com.example.bikini_android.ui.feeds.FeedsViewModel
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
    private lateinit var viewModel: FeedsViewModel
    private val itemEventRelay: Relay<RxAction> by lazy {
        viewModel.itemEventRelay
    }

    private val feedMarkerBindingTable = ArrayMap<Feed, ViewFeedMarkerBinding>()
    private val feedBoundTable = ArrayMap<String, Feed>()
    private var isLoadUiData = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        DataBindingUtil.inflate<FragmentBikiniMapBinding>(inflater, R.layout.fragment_bikini_map, container, false)
            .also {
                super.onCreateView(inflater, container, savedInstanceState)
                binding = it
                viewModel = ViewModelProvider(requireActivity())[FeedsViewModel::class.java]
            }.root

    override fun onMapReady(googleMap: GoogleMap?) {
        super.onMapReady(googleMap)
        observeEvent()
        loadUiData()
        initMap()
    }

    override fun onDestroyView() {
        feedMarkerBindingTable.clear()
        super.onDestroyView()
    }

    private fun loadUiData() {
        if (!isLoadUiData) {
            viewModel.loadFeeds()
            isLoadUiData = true
        }
    }

    private fun initMap() {
        map.setOnMarkerClickListener { marker ->
            feedBoundTable[marker.tag]?.let { feed ->
                findNavController().navigate(
                    R.id.action_bikini_map_to_feeds_end,
                    FeedsFragment.makeBundle(
                        RecyclerViewLayoutType.GRID,
                        FeedsType.NEAR_LOCATION_FEEDS,
                        FeedSortType.NEAR,
                        feed
                    )
                )
            }
            true
        }
    }

    private fun observeEvent() {
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
                bindFeedMarkers(event.feeds)
            }.addTo(disposables)
    }

    private fun addMarker(feed: Feed) {
        feedMarkerBindingTable[feed]?.root?.let { view ->
            feed.position?.let { position ->
                map.addMarker(GoogleMapUtils.getFeedMarkerOption(view, position))
                    .apply {
                        tag = feed.feedId
                    }
                    .also {
                        feedBoundTable[feed.feedId] = feed
                    }
            }
        }
    }

    private fun bindFeedMarkers(feeds: List<Feed>) {
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
}