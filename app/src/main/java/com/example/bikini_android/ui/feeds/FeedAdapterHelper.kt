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
import com.example.bikini_android.ui.common.RecyclerViewLayoutType
import com.example.bikini_android.util.bus.RxAction
import com.jakewharton.rxrelay2.Relay


/**
 * @author MyeongKi
 */

class FeedAdapterHelper(private val layoutType: RecyclerViewLayoutType = RecyclerViewLayoutType.LINEAR) {

    fun getLayoutManger(context: Context): RecyclerView.LayoutManager {
        return when (layoutType) {
            RecyclerViewLayoutType.GRID ->
                GridLayoutManager(context, FEED_GRID_SIZE)
            RecyclerViewLayoutType.LINEAR ->
                LinearLayoutManager(context)
            RecyclerViewLayoutType.VERTICAL ->
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    fun getFeedItemViewModel(feed: Feed, itemEventRelay: Relay<RxAction>): FeedItemViewModel {
        return when (layoutType) {
            RecyclerViewLayoutType.VERTICAL, RecyclerViewLayoutType.GRID ->
                FeedGridItemViewModel(feed)
            RecyclerViewLayoutType.LINEAR ->
                FeedLinearItemViewModel(feed)
        }.apply {
            this.itemEventRelay = itemEventRelay
        }
    }

    companion object {
        private const val FEED_GRID_SIZE = 4
    }
}