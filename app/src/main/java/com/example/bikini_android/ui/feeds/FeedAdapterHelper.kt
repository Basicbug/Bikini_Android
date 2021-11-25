/*
 * FeedBindHelper.kt 2020. 12. 8
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.feeds

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bikini_android.repository.feed.Feed
import com.basicbug.core.ui.list.RecyclerViewLayoutType
import com.basicbug.core.ui.list.DefaultListAdapter
import com.basicbug.core.util.bus.RxAction
import com.jakewharton.rxrelay2.Relay

/**
 * @author MyeongKi
 */

class FeedAdapterHelper(
    val feedsAdapter: DefaultListAdapter<FeedItemViewModel>,
    private val layoutType: RecyclerViewLayoutType = RecyclerViewLayoutType.VERTICAL,
    private val itemEventRelay: Relay<RxAction>
) {
    fun getLayoutManger(context: Context): RecyclerView.LayoutManager {
        return when (layoutType) {
            RecyclerViewLayoutType.GRID ->
                GridLayoutManager(context, FEED_GRID_SIZE)
            RecyclerViewLayoutType.HORIZONTAL ->
                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            RecyclerViewLayoutType.VERTICAL ->
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    fun bindFeeds(feeds: List<Feed>) {
        feedsAdapter.submitList(
            feeds.map {
                getFeedItemViewModel(it)
            }
        )
    }

    private fun getFeedItemViewModel(feed: Feed): FeedItemViewModel {
        return when (layoutType) {
            RecyclerViewLayoutType.HORIZONTAL, RecyclerViewLayoutType.GRID ->
                FeedGridItemViewModel(feed, itemEventRelay)
            RecyclerViewLayoutType.VERTICAL ->
                FeedVerticalItemViewModel(feed, itemEventRelay)
        }
    }

    companion object {
        private const val FEED_GRID_SIZE = 4
    }
}