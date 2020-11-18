/*
 * GenericBindingViewHolder.kt 2020. 11. 16
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.common.list

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.bikini_android.BR

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