/*
 * SettingsFragment.kt 2021. 4. 3
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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.basicbug.core.util.ktx.autoCleared
import com.example.bikini_android.R
import com.example.bikini_android.databinding.FragmentSettingsBinding
import com.example.bikini_android.ui.base.BikiniBaseFragment
import com.basicbug.core.ui.list.DefaultDiffCallback
import com.basicbug.core.ui.list.DefaultListAdapter

/**
 * @author MyeongKi
 */

class SettingsFragment : BikiniBaseFragment() {
    private var binding by autoCleared<FragmentSettingsBinding>()
    private lateinit var viewModel: SettingsViewModel
    private var settingsType: SettingsType = SettingsType.MAIN
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            settingsType =
                SettingsType.valueOf(
                    it.getString(KEY_SETTINGS_TYPE)
                        ?: SettingsType.MAIN.name
                )
        }
    }

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
        viewModel = ViewModelProvider(this, SettingsViewModelFactoryProvider())
            .get(SettingsViewModelFactoryProvider.getSettingsViewModelClazz(settingsType))
        binding = it.apply {
            settings.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            settings.adapter = DefaultListAdapter(DefaultDiffCallback()).apply {
                submitList(viewModel.getSettingsItemViewModels())
            }
        }
    }.root

    companion object {
        private const val KEY_SETTINGS_TYPE = "settingsType"

        fun makeBundle(
            settingsType: SettingsType
        ): Bundle {
            return Bundle().apply {
                putString(KEY_SETTINGS_TYPE, settingsType.name)
            }
        }
    }
}
