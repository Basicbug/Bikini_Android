package com.example.bikini_android.ui.common.list

import androidx.recyclerview.widget.DiffUtil
import com.example.bikini_android.ui.common.item.ItemViewModel

/**
 * @author bsgreentea
 */
class DefaultDiffCallback<T : ItemViewModel> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}