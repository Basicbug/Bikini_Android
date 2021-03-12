package com.example.bikini_android.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.bikini_android.R
import com.example.bikini_android.app.AppResources
import com.example.bikini_android.databinding.ActivityLoginBinding
import com.example.bikini_android.manager.login.LoginManagerProxy
import com.example.bikini_android.manager.login.NaverOAuthLoginHandler
import com.example.bikini_android.ui.base.BaseActivity
import com.example.bikini_android.ui.holder.MainHolderActivity
import com.example.bikini_android.util.rx.addTo
import com.nhn.android.naverlogin.OAuthLogin
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * @author bsgreentea
 */
class LoginActivity : BaseActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.apply {
            naverOAuthHandler = NaverOAuthLoginHandler()
//            naverLogin.setOAuthLoginHandler(NaverOAuthLoginHandler())
        }

//        binding.naverLogin.setOAuthLoginHandler(NaverOAuthLoginHandler())

        observeEvent()
    }

    private fun observeEvent() {
        LoginManagerProxy.loginEventRelay
            .ofType(LoginEvent::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                startActivity(Intent(this, MainHolderActivity::class.java))
                finish()
            }.addTo(disposables)
    }
}