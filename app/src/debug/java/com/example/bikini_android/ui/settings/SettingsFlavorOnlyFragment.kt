/*
 * SettingsFlavorOnlyFragment.kt 2021. 4. 4
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bikini_android.R
import com.example.bikini_android.databinding.FragmentFlavorOnlySettingsBinding
import com.example.bikini_android.ui.base.BaseFragment
import com.example.bikini_android.ui.common.list.DefaultDiffCallback
import com.example.bikini_android.ui.common.list.DefaultListAdapter

/**
 * @author MyeongKi
 */
class SettingsFlavorOnlyFragment : BaseFragment() {
    private var binding: FragmentFlavorOnlySettingsBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = DataBindingUtil.inflate<FragmentFlavorOnlySettingsBinding>(
        inflater,
        R.layout.fragment_flavor_only_settings,
        container,
        false
    ).also {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = it.apply {
            settings.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            settings.adapter = DefaultListAdapter(DefaultDiffCallback()).apply {
                submitList(SettingItemsProviderImpl.createSettingItemsFlavorOnly(getNavigationHelper()))
            }
        }
    }.root
}
