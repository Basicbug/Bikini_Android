package com.example.bikini_android.ui.feeds.adapter

import androidx.recyclerview.widget.DiffUtil

/**
 * @author bsgreentea
 */
class FeedItemDiffCallback<T : FeedItemViewModel> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.userId == newItem.userId
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.toString() == newItem.toString()
    }

}