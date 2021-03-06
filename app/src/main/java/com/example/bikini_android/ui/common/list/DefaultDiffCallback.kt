package com.example.bikini_android.ui.common.list

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.bikini_android.ui.common.item.ItemViewModel

/**
 * @author bsgreentea
 */
class DefaultDiffCallback<T : ItemViewModel> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.getItemHashCode() == newItem.getItemHashCode()
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.getContentsHashCode() == newItem.getContentsHashCode()
    }
}