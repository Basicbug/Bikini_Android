/*
 * SplashActivity.kt 2020. 12. 17
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.splash

import android.content.Intent
import android.os.Bundle
import com.basicbug.core.app.AppResources
import com.basicbug.core.rx.addTo
import com.basicbug.core.ui.base.BaseActivity
import com.example.bikini_android.R
import com.example.bikini_android.manager.login.LoginManagerProxy
import com.example.bikini_android.ui.holder.MainHolderActivity
import com.example.bikini_android.ui.login.LoginActivity
import com.nhn.android.naverlogin.OAuthLogin
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

/**
 * @author MyeongKi
 */

class SplashActivity : BaseActivity() {

    init {
        OAuthLogin.getInstance().apply {
            init(
                AppResources.getContext(),
                AppResources.getString(R.string.naver_login_client_id),
                AppResources.getString(R.string.naver_login_client_secret),
                AppResources.getString(R.string.app_name)
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)
        Observable.timer(SPLASH_TIME.toLong(), TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { finishInternal() }.addTo(disposables)
    }

    private fun finishInternal() {

        if (isAlreadyLoggedIn()) {
            startActivity(Intent(this, MainHolderActivity::class.java))
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        finish()
    }

    private fun isAlreadyLoggedIn() = LoginManagerProxy.isLoggedIn()

    override fun finish() {
        super.finish()
        overridePendingTransition(0, R.anim.fade_out)
    }

    companion object {
        private const val SPLASH_TIME = 1
    }
}
