package com.example.bikini_android.ui.feeds.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.bikini_android.R
import com.example.bikini_android.repository.feed.Feed


/**
 * @author bsgreentea
 */
class FeedAdapter : ListAdapter<Feed, FeedViewHolder>(
    FeedDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        return FeedViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_feed, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setItems(items: List<Feed>) {
        submitList(items)
    }
}