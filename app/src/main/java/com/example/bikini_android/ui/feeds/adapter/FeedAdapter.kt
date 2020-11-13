package com.example.bikini_android.ui.feeds.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bikini_android.R
import com.example.bikini_android.repository.feed.Feed
import com.example.bikini_android.ui.feeds.types.ItemViewType


/**
 * @author bsgreentea
 */
class FeedAdapter : ListAdapter<Feed, RecyclerView.ViewHolder>(
    FeedDiffCallback()
) {

    private var currentViewType = ItemViewType.LINEAR.type

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ItemViewType.LINEAR.type) {
            FeedViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_feed, parent, false
                )
            )
        } else {
            FeedGridViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_feed_grid, parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is FeedViewHolder) {
            holder.bind(getItem(position))
        } else if (holder is FeedGridViewHolder) {
            holder.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentViewType
    }

    fun setItems(items: List<Feed>) {
        submitList(items)
    }

    fun setViewType(viewType: Int) {
        currentViewType = viewType
        notifyDataSetChanged()
    }
}