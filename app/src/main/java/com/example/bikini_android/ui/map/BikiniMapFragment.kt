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
import com.example.bikini_android.repository.feed.LocationInfo
import com.example.bikini_android.ui.base.BaseMapFragment
import com.example.bikini_android.ui.common.RecyclerViewLayoutType
import com.example.bikini_android.ui.feeds.FeedsEvent
import com.example.bikini_android.ui.feeds.FeedsFragment
import com.example.bikini_android.ui.feeds.FeedsSortType
import com.example.bikini_android.ui.feeds.FeedsType
import com.example.bikini_android.ui.feeds.viewmodel.FeedsViewModel
import com.example.bikini_android.ui.feeds.viewmodel.FeedsViewModelFactoryProvider
import com.example.bikini_android.ui.map.viewmodel.BikiniMapViewModel
import com.example.bikini_android.ui.map.viewmodel.MapViewModelFactoryProvider
import com.example.bikini_android.util.ktx.autoCleared
import com.example.bikini_android.util.rx.addTo
import com.google.android.gms.maps.GoogleMap
import com.google.maps.android.clustering.ClusterManager
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * @author MyeongKi
 */

class BikiniMapFragment : BaseMapFragment() {
    private var binding by autoCleared<FragmentBikiniMapBinding>()
    private lateinit var feedsViewModel: FeedsViewModel
    private var clusterManager by autoCleared<ClusterManager<Feed>>()
    private val feedMarkerBindingTable = ArrayMap<String, ViewFeedMarkerBinding>()
    private val feedAddedToMapTable = ArrayMap<String, Feed>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapViewModel = ViewModelProvider(
            this,
            MapViewModelFactoryProvider(this, savedInstanceState)
        )[BikiniMapViewModel::class.java]
        feedsViewModel = ViewModelProvider(
            requireActivity(),
            FeedsViewModelFactoryProvider(
                requireActivity(),
                savedInstanceState
            )
        ).get(FeedsViewModelFactoryProvider.getFeedViewModelClazz(MAP_FEEDS_TYPE))
        arguments?.let {
            mapViewModel.locationFocused = it.getParcelable(KEY_LOCATION_INFO)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        DataBindingUtil.inflate<FragmentBikiniMapBinding>(
            inflater,
            R.layout.fragment_bikini_map,
            container,
            false
        ).also {
            super.onCreateView(inflater, container, savedInstanceState)
            binding = it
            mapView = it.map.also { view ->
                view.getMapAsync(this)
            }
        }.root

    override fun onMapReady(googleMap: GoogleMap?) {
        super.onMapReady(googleMap)
        observeEvent()
        initMap()
        feedsViewModel.loadFeeds()
    }

    override fun onDestroyView() {
        clearFeedTable()
        super.onDestroyView()
    }

    private fun initMap() {
        map.get()?.let { map ->
            clusterManager = ClusterManager<Feed>(requireContext(), map)
            clusterManager.apply {
                renderer =
                    FeedRenderer(
                        feedMarkerBindingTable,
                        map,
                        this
                    )
                setOnClusterClickListener { cluster ->
                    cluster?.items?.let { feeds ->
                        navigateNearLocationFeeds(feeds.toList())
                        true
                    }
                    false
                }
                setOnClusterItemClickListener { item ->
                    item?.let {
                        navigateNearLocationFeeds(listOf(it))
                        true
                    }
                    false
                }
            }

        }
    }

    private fun navigateNearLocationFeeds(feeds: List<Feed>) {
        if (feeds.isNotEmpty()) {
            getNavigationHelper()?.navigateToBikiniFeeds(
                FeedsFragment.makeBundle(
                    RecyclerViewLayoutType.GRID,
                    MAP_FEEDS_TYPE,
                    FeedsSortType.NEAR_DISTANCE,
                    feeds.first(),
                    feeds
                )
            )
        }
    }

    private fun observeEvent() {
        fragmentItemEventRelay
            .ofType(FeedMarkerImageLoadEvent::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { event ->
                addMarker(event.feed)
            }.addTo(disposables)

        fragmentItemEventRelay
            .ofType(MapLocationChangeEvent::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { event ->
                clusterManager.cluster()
                feedsViewModel.loadFeeds(event.latLng, event.visibleRadius)
            }.addTo(disposables)

        feedsViewModel.itemEventRelay
            .ofType(FeedsEvent::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .filter { it.feedsType == MAP_FEEDS_TYPE }
            .subscribe { event ->
                bindFeedMarkers(event.feeds)
            }.addTo(disposables)
    }

    private fun clearFeedTable() {
        feedAddedToMapTable.clear()
        feedMarkerBindingTable.clear()
    }

    private fun addMarker(feed: Feed) {
        clusterManager.addItem(feed)
        clusterManager.cluster()
    }

    private fun bindFeedMarkers(feeds: List<Feed>) {
        for (feed in feeds) {
            if (feedMarkerBindingTable[feed.feedId] == null) {
                getFeedMarkerBinding().run {
                    feedMarkerBindingTable[feed.feedId] = this
                    apply {
                        viewmodel = FeedMarkerItemViewModel(feed).also {
                            it.itemEventRelay = fragmentItemEventRelay
                        }
                        executePendingBindings()
                    }
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
        private const val KEY_LOCATION_INFO = "keyLocationInfo"
        private val MAP_FEEDS_TYPE = FeedsType.NEARBY_FEEDS

        fun makeBundle(
            location: LocationInfo
        ): Bundle {
            return Bundle().apply {
                putParcelable(KEY_LOCATION_INFO, location)
            }
        }
    }
}
