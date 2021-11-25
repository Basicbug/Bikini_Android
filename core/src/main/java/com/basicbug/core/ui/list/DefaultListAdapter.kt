/*
 * DefaultListAdapter.kt 2021. 12. 2
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.core.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.basicbug.core.ui.item.ItemViewModel

/**
 * @author MyeongKi
 */

open class DefaultListAdapter<T : ItemViewModel>(diffCallback: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, GenericBindingViewHolder<T>>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericBindingViewHolder<T> {
        return GenericBindingViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                viewType,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GenericBindingViewHolder<T>, position: Int) {
        val item = getItem(position)
        holder.onBindView(item)
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return item.getLayoutRes()
    }
}

