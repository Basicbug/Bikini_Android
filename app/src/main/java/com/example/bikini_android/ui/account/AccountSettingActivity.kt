package com.example.bikini_android.ui.account

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.bikini_android.R
import com.example.bikini_android.databinding.ActivityAccountSettingBinding
import com.example.bikini_android.ui.base.BaseActivity
import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.rx.addTo
import com.jakewharton.rxrelay2.Relay
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * @author bsgreentea
 */
class AccountSettingActivity : BaseActivity() {

    private lateinit var binding: ActivityAccountSettingBinding
    private lateinit var viewModel: AccountSettingViewModel
    private lateinit var itemEventRelay: Relay<RxAction>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_account_setting)
        viewModel = ViewModelProvider(this)[AccountSettingViewModel::class.java]

        binding.apply {
            viewmodel = viewModel
        }

        itemEventRelay = viewModel.itemEventRelay

        setUpObservers()
    }

    private fun setUpObservers() {
        itemEventRelay
            .ofType(AccountSettingViewModel.EventType::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { type ->

                when (type) {
                    AccountSettingViewModel.EventType.UPDATE_SUCCEED -> {
                        finish()
                    }
                    else -> throw IllegalStateException("Undefined type")
                }

            }
            .addTo(disposables)
    }
}