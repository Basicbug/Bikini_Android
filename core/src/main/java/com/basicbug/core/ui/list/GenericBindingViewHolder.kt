/*
 * GenericBindingViewHolder.kt 2021. 12. 2
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.core.ui.list

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.basicbug.core.BR

/**
 * @author MyeongKi
 */

class GenericBindingViewHolder<T>(private val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {
    var viewModel: T? = null

    fun onBindView(viewModel: T) {
        viewModel?.let {
            this.viewModel = it
            binding.setVariable(BR.viewmodel, viewModel)
        }
        binding.executePendingBindings()
    }

}