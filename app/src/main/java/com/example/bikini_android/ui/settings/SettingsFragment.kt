/*
 * SettingsFragment.kt 2020. 11. 1
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.bikini_android.R
import com.example.bikini_android.databinding.FragmentSettingsBinding
import com.example.bikini_android.ui.holder.MainHolderFragment

/**
 * @author MyeongKi
 */

class SettingsFragment : MainHolderFragment() {
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = DataBindingUtil.inflate<FragmentSettingsBinding>(inflater, R.layout.fragment_settings, container, false)
        .also {
            binding = it
        }.root

    companion object {
        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }
}