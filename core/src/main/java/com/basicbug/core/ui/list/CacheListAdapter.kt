/*
 * CacheListAdapter.kt 2021. 12. 2
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.core.ui.list

import androidx.recyclerview.widget.DiffUtil
import com.basicbug.core.ui.item.CacheItemViewModel

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