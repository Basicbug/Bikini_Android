/*
 * FeedRenderer.kt 2021. 5. 16
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.map

import android.util.ArrayMap
import com.example.bikini_android.app.AppResources
import com.example.bikini_android.databinding.ViewFeedMarkerBinding
import com.example.bikini_android.repository.feed.Feed
import com.example.bikini_android.util.image.BitmapUtils
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

/**
 * @author MyeongKi
 */
class FeedRenderer(
    private val feedMarkerBindingTable: ArrayMap<String, ViewFeedMarkerBinding>,
    map: GoogleMap,
    clusterManager: ClusterManager<Feed>
) : DefaultClusterRenderer<Feed>(AppResources.getContext(), map, clusterManager) {

    override fun onBeforeClusterItemRendered(item: Feed, markerOptions: MarkerOptions) {
        feedMarkerBindingTable[item.feedId]?.let {
            notifyBadgeCountChange(it, DEFAULT_BADGE_SIZE)
            markerOptions.icon(getItemIcon(it))
        }
    }

    override fun onClusterItemUpdated(item: Feed, marker: Marker) {
        feedMarkerBindingTable[item.feedId]?.let {
            notifyBadgeCountChange(it, DEFAULT_BADGE_SIZE)
            marker.setIcon(getItemIcon(it))
        }
    }

    override fun onBeforeClusterRendered(cluster: Cluster<Feed>, markerOptions: MarkerOptions) {
        feedMarkerBindingTable[cluster.items.first()?.feedId]?.let {
            notifyBadgeCountChange(it, cluster.size)
            markerOptions.icon(getItemIcon(it))
        }
    }

    override fun onClusterUpdated(cluster: Cluster<Feed>, marker: Marker) {
        feedMarkerBindingTable[cluster.items.first()?.feedId]?.let {
            notifyBadgeCountChange(it, cluster.size)
            marker.setIcon(getItemIcon(it))
        }
    }

    private fun notifyBadgeCountChange(viewBinding: ViewFeedMarkerBinding, badgeCount: Int) {
        viewBinding.apply {
            viewmodel?.badgeCount = badgeCount
            executePendingBindings()
        }
    }

    private fun getItemIcon(iconBinding: ViewFeedMarkerBinding): BitmapDescriptor {
        return BitmapUtils.convertBitmapDescriptor(iconBinding.root)
    }

    override fun shouldRenderAsCluster(cluster: Cluster<Feed>): Boolean {
        return cluster.size > 1
    }

    companion object {
        const val DEFAULT_BADGE_SIZE = 1
    }
}