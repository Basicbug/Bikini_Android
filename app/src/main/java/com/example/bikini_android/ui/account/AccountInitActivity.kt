package com.example.bikini_android.ui.account

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.bikini_android.R
import com.example.bikini_android.databinding.ActivityAccountInitBinding
import com.example.bikini_android.ui.base.BaseActivity
import com.example.bikini_android.ui.holder.MainHolderActivity
import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.rx.addTo
import com.jakewharton.rxrelay2.Relay
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * @author bsgreentea
 */
class AccountInitActivity : BaseActivity() {

    lateinit var binding: ActivityAccountInitBinding
    private lateinit var viewModel: AccountViewModel
    private lateinit var itemEventRelay: Relay<RxAction>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_account_init)
        viewModel = ViewModelProvider(this)[AccountViewModel::class.java]

        binding.apply {
            viewmodel = viewModel
        }

        itemEventRelay = viewModel.itemEventRelay

        setUpObserver()

    }

    private fun setUpObserver() {
        itemEventRelay
            .ofType(AccountViewModel.EventType::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { type ->

                when (type) {
                    AccountViewModel.EventType.UPDATE_SUCCEED -> {
                        startMainHolder()
                    }
                    else -> throw IllegalStateException("Undefined type")
                }

                startActivity(Intent(this, MainHolderActivity::class.java))
                finish()
            }
            .addTo(disposables)
    }

    private fun startMainHolder() {
        startActivity(Intent(this, MainHolderActivity::class.java))
        finish()
    }

}