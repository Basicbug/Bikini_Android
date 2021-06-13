/*
 * SettingsImageCompressionFragment.kt 2021. 6. 13
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.settings.image

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bikini_android.R
import com.example.bikini_android.databinding.FragmentSettingsBinding
import com.example.bikini_android.ui.base.BaseFragment
import com.example.bikini_android.ui.common.list.DefaultDiffCallback
import com.example.bikini_android.ui.common.list.DefaultListAdapter
import com.example.bikini_android.util.ktx.autoCleared

/**
 * @author MyeongKi
 */
class SettingsImageCompressionFragment : BaseFragment() {
    private var binding by autoCleared<FragmentSettingsBinding>()
    private lateinit var viewModel: SettingsImageCompressionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = DataBindingUtil.inflate<FragmentSettingsBinding>(
        inflater,
        R.layout.fragment_settings,
        container,
        false
    ).also {
        super.onCreateView(inflater, container, savedInstanceState)
        viewModel = ViewModelProvider(this)[SettingsImageCompressionViewModel::class.java]
        binding = it.apply {
            settings.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            settings.adapter = DefaultListAdapter(DefaultDiffCallback()).apply {
                submitList(
                    viewModel.getSettingsImageCompressionItemViewModels()
                )
            }
        }
    }.root
}
