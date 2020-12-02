package com.example.bikini_android.ui.common.list

import androidx.recyclerview.widget.DiffUtil
import com.example.bikini_android.ui.common.item.ItemViewModel

/**
 * @author bsgreentea
 */
abstract class DefaultDiffCallback<T : ItemViewModel> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        TODO("Not yet implemented")
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        TODO("Not yet implemented")
    }
}