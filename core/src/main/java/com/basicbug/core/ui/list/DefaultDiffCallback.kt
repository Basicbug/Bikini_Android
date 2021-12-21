/*
 * DefaultDiffCallback.kt 2021. 12. 2
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.core.ui.list

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.basicbug.core.ui.item.ItemViewModel

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