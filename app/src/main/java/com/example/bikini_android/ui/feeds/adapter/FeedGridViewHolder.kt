package com.example.bikini_android.ui.feeds.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.bikini_android.databinding.ItemFeedGridBinding
import com.example.bikini_android.repository.feed.Feed

/**
 * @author bsgreentea
 */
class FeedGridViewHolder(
    private val binding: ItemFeedGridBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val feedGridViewModel = FeedItemGridViewModel().apply {
        binding.feedItemGridViewModel = this
    }

    fun bind(feed: Feed) {
        feedGridViewModel.imageUri = feed.imageUri
    }
}