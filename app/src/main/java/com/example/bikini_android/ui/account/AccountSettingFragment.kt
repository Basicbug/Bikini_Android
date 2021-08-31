package com.example.bikini_android.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.bikini_android.R
import com.example.bikini_android.databinding.FragmentAccountSettingBinding
import com.example.bikini_android.manager.login.LoginManagerProxy
import com.example.bikini_android.ui.base.BaseFragment
import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.ktx.autoCleared
import com.example.bikini_android.util.rx.addTo
import com.jakewharton.rxrelay2.Relay
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * @author bsgreentea
 */
class AccountSettingFragment : BaseFragment() {

    private var binding by autoCleared<FragmentAccountSettingBinding>()

    private lateinit var viewModel: AccountSettingViewModel
    private lateinit var itemEventRelay: Relay<RxAction>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = DataBindingUtil.inflate<FragmentAccountSettingBinding>(
        inflater,
        R.layout.fragment_account_setting,
        container,
        false
    ).also {
        super.onCreateView(inflater, container, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            AccountViewModelFactory(AccountSettingItemViewModel())
        )[AccountSettingViewModel::class.java]

        binding = it.apply {
            viewmodel = viewModel
        }

        itemEventRelay = viewModel.itemEventRelay

        setUpObservers()

    }.root

    private fun setUpObservers() {

        itemEventRelay
            .ofType(AccountEvent::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                viewModel.progressViewModel.isVisible = false

                when (it.type) {
                    AccountSettingViewModel.EventType.UPDATE_SUCCEED -> {
                        LoginManagerProxy.userName = it.result
                        getNavigationHelper()?.popBackStack()
                    }

                    AccountSettingViewModel.EventType.UPDATE_FAILED -> {
                        viewModel.showError(it.result)
                    }
                }

            }
            .addTo(disposables)
    }
}