/*
 * ProfileDetailFragment.kt 2021. 10. 23
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.account.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.basicbug.core.util.ktx.autoCleared
import com.example.bikini_android.R
import com.example.bikini_android.databinding.FragmentAccountDetailBinding
import com.example.bikini_android.manager.login.LoginManagerProxy
import com.example.bikini_android.ui.base.BikiniBaseFragment
import com.example.bikini_android.ui.login.LoginActivity

/**
 * @author bsgreentea
 */
class AccountDetailFragment : BikiniBaseFragment() {

    private var binding by autoCleared<FragmentAccountDetailBinding>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = DataBindingUtil.inflate<FragmentAccountDetailBinding>(
        inflater,
        R.layout.fragment_account_detail,
        container,
        false
    ).also {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = it
        binding.logout.setOnClickListener {
            LoginManagerProxy.logOut()
            startActivity(Intent(activity, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            })
        }

        binding.account.setOnClickListener {
            getNavigationHelper()?.navigateToAccountSetting()
        }

    }.root
}