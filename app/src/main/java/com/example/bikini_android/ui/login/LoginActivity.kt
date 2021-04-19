package com.example.bikini_android.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.bikini_android.R
import com.example.bikini_android.databinding.ActivityLoginBinding
import com.example.bikini_android.manager.login.LoginManagerProxy
import com.example.bikini_android.manager.login.naver.NaverOAuthLoginHandler
import com.example.bikini_android.network.client.ApiClientHelper
import com.example.bikini_android.network.request.service.AuthService
import com.example.bikini_android.ui.base.BaseActivity
import com.example.bikini_android.ui.holder.MainHolderActivity
import com.example.bikini_android.util.logging.Logger
import com.example.bikini_android.util.rx.addTo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author bsgreentea
 */
class LoginActivity : BaseActivity() {

    private val logger = Logger().apply {
        TAG = this@LoginActivity.javaClass.simpleName
    }

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.apply {
            naverOAuthHandler =
                NaverOAuthLoginHandler()
        }

        observeEvent()
    }

    private fun observeEvent() {
        LoginManagerProxy.loginEventRelay
            .ofType(LoginEvent::class.java)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                sendTokenToServer(it.accessToken)
            }
            .addTo(disposables)
    }

    private fun sendTokenToServer(accessToken: String) {
        ApiClientHelper
            .createMainApiByService(AuthService::class)
            .loginNaver(accessToken)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                startActivity(Intent(this, MainHolderActivity::class.java))
                finish()
            }, {
                logger.error { it.toString() }
            })
            .addTo(disposables)
    }
}