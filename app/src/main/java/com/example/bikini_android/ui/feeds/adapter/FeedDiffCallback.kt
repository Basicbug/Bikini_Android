package com.example.bikini_android.ui.feeds.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.bikini_android.repository.feed.Feed

/**
 * @author bsgreentea
 */
class FeedDiffCallback : DiffUtil.ItemCallback<Feed>() {
    override fun areItemsTheSame(oldItem: Feed, newItem: Feed): Boolean {
        return oldItem.userId == newItem.userId && oldItem.feedNumOfUser == newItem.feedNumOfUser
    }

    override fun areContentsTheSame(oldItem: Feed, newItem: Feed): Boolean {
        return oldItem.toString() == newItem.toString()
    }

}