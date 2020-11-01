/*
 * BikiniMapFragment.kt 2020. 11. 1
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.bikini_android.R
import com.example.bikini_android.databinding.FragmentBikiniMapBinding
import com.example.bikini_android.ui.holder.MainHolderFragment

/**
 * @author MyeongKi
 */

class BikiniMapFragment :MainHolderFragment(){
    private lateinit var binding:FragmentBikiniMapBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bikini_map, container, false)

        return binding.root
    }

    companion object{
        fun newInstance(): BikiniMapFragment {
            return BikiniMapFragment()
        }
    }
}