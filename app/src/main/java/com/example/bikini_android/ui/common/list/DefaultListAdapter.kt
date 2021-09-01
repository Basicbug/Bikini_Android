/*
 * DefaultListAdapter.kt 2020. 11. 16
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.common.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.bikini_android.ui.common.item.ItemViewModel

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

