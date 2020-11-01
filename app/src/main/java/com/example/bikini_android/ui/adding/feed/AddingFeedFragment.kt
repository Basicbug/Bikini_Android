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

/**
 * @author MyeongKi
 */

class AddingFeedFragment :MainHolderFragment(){
    private lateinit var binding: FragmentAddingFeedBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_adding_feed, container, false)

        return binding.root
    }
    companion object{
        fun newInstance(): AddingFeedFragment {
            return AddingFeedFragment()
        }
    }
}