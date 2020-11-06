/*
 * AddingFeedFragment.kt 2020. 11. 1
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.adding.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.bikini_android.R
import com.example.bikini_android.databinding.FragmentAddingFeedBinding
import com.example.bikini_android.ui.holder.MainHolderFragment
import com.example.bikini_android.util.logging.Logger

/**
 * @author MyeongKi
 */

class AddingFeedFragment : MainHolderFragment() {

    private val logger: Logger by lazy {
        Logger().apply {
            TAG = "AddingFeedFragment"
        }
    }

    private lateinit var binding: FragmentAddingFeedBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        DataBindingUtil.inflate<FragmentAddingFeedBinding>(inflater, R.layout.fragment_adding_feed, container, false)
            .also {
                binding = it
            }.root

    override fun onResume() {
        super.onResume()
        logger.debug { "onResume" }
    }

    companion object {
        fun newInstance(): AddingFeedFragment {
            return AddingFeedFragment()
        }
    }
}
