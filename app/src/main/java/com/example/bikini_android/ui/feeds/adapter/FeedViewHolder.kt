package com.example.bikini_android.ui.feeds.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.bikini_android.databinding.ItemFeedBinding
import com.example.bikini_android.repository.feed.Feed

/**
 * @author bsgreentea
 */
class FeedViewHolder(
    private val binding: ItemFeedBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val feedViewModel = FeedItemViewModel().apply {
        binding.feedItemViewModel = this
    }

    fun bind(feed: Feed) {
        feedViewModel.userId = feed.userId
    }
}