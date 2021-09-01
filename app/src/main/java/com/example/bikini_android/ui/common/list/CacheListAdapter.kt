/*
 * RefreshListAdapter.kt 2021. 8. 7
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.common.list

import androidx.recyclerview.widget.DiffUtil
import com.example.bikini_android.ui.common.item.CacheItemViewModel

/**
 * @author MyeongKi
 */
class CacheListAdapter<T : CacheItemViewModel>(diffCallback: DiffUtil.ItemCallback<T>) :
    DefaultListAdapter<T>(diffCallback) {
    override fun submitList(list: MutableList<T>?) {
        super.submitList(list)
        currentList.forEach {
            it.synchronize()
        }
    }
}