/*
 * SettingsImageFragment.kt 2021. 6. 13
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
class SettingsImageFragment : BaseFragment() {
    private var binding by autoCleared<FragmentSettingsBinding>()
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
        binding = it.apply {
            settings.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            settings.adapter = DefaultListAdapter(DefaultDiffCallback()).apply {
                submitList(
                    SettingsImageConfigItemProvider.createImageSettingItems(
                        getNavigationHelper()
                    )
                )
            }
        }
    }.root
}
//해당 페이지는 공통화로 작업하고 번들에서 해당 뷰모델 타입을 선언받고 뷰모델 팩토리에서 해당 타입을 통하여 적당한 뷰모델을 주입하는 방식으로 로직 간소화가 가능해 보임