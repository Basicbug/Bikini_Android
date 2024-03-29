/*
 * AccountInitActivity.kt 2021. 10. 23
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.account.setting

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.basicbug.core.app.ToastHelper
import com.basicbug.core.rx.addTo
import com.basicbug.core.ui.base.BaseActivity
import com.basicbug.core.util.bus.RxAction
import com.basicbug.core.util.bus.RxActionBus
import com.example.bikini_android.R
import com.example.bikini_android.databinding.ActivityAccountInitBinding
import com.example.bikini_android.manager.login.LoginManagerProxy
import com.example.bikini_android.ui.holder.MainHolderActivity
import com.example.bikini_android.ui.account.viewmodel.AccountViewModelFactory
import com.jakewharton.rxrelay2.Relay
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * @author bsgreentea
 */
class AccountInitActivity : BaseActivity() {

    lateinit var binding: ActivityAccountInitBinding
    private lateinit var viewModel: AccountSettingViewModel
    private lateinit var itemEventRelay: Relay<RxAction>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_account_init)
        viewModel = ViewModelProvider(
            this,
            AccountViewModelFactory(AccountInitItemViewModel())
        )[AccountSettingViewModel::class.java]

        binding.apply {
            viewmodel = viewModel
        }

        itemEventRelay = viewModel.itemEventRelay

        setUpObserver()

    }

    private fun setUpObserver() {
        RxActionBus
            .toObservable(AccountSettingEvent::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                viewModel.progressViewModel.isVisible = false

                when (it.type) {
                    AccountSettingViewModel.EventType.UPDATE_SUCCEED -> {
                        ToastHelper.show(R.string.success_edit_nickname)
                        LoginManagerProxy.userName = it.result
                        startMainHolder()
                    }

                    AccountSettingViewModel.EventType.UPDATE_FAILED -> {
                        viewModel.showError(it.result)
                    }
                }

            }
            .addTo(disposables)
    }

    private fun startMainHolder() {
        startActivity(Intent(this, MainHolderActivity::class.java))
        finish()
    }

}