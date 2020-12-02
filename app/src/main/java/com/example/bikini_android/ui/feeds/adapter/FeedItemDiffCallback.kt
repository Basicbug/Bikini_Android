package com.example.bikini_android.ui.feeds.adapter

import com.example.bikini_android.ui.common.list.DefaultDiffCallback

/**
 * @author bsgreentea
 */
class FeedItemDiffCallback<T : FeedItemViewModel> : DefaultDiffCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.toString() == newItem.toString()
    }

}