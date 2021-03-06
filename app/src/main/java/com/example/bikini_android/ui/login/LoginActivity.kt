package com.example.bikini_android.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.bikini_android.R
import com.example.bikini_android.databinding.ActivityLoginBinding
import com.example.bikini_android.manager.login.LoginManagerProxy
import com.example.bikini_android.manager.login.naver.NaverOAuthLoginHandler
import com.example.bikini_android.ui.base.BaseActivity
import com.example.bikini_android.ui.holder.MainHolderActivity
import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.rx.addTo
import com.jakewharton.rxrelay2.Relay
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * @author bsgreentea
 */
@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var itemEventRelay: Relay<RxAction>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        binding.apply {
            naverOAuthHandler = NaverOAuthLoginHandler()
            viewmodel = viewModel
        }

        itemEventRelay = viewModel.itemEventRelay

        setUpObservers()
    }

    private fun setUpObservers() {
        LoginManagerProxy.loginEventRelay
            .ofType(LoginEvent::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                viewModel.sendTokenToServer(it.accessToken)
            }
            .addTo(disposables)

        itemEventRelay
            .ofType(LoginViewModel.CompleteEvent::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                startActivity(Intent(this, MainHolderActivity::class.java))
                finish()
            }
            .addTo(disposables)
    }
}