package com.example.bikini_android.manager.login

import android.util.Log
import com.example.bikini_android.ui.login.LoginEvent
import com.nhn.android.naverlogin.OAuthLoginHandler

/**
 * @author bsgreentea
 */
class NaverOAuthLoginHandler : OAuthLoginHandler() {
    override fun run(success: Boolean) {
        if (success) {

            LoginManagerProxy.setLoginManager(LoginManagerFactory.createFactory<NaverLoginManager>().makeLoginManager())
            LoginManagerProxy.successLogin()
            LoginManagerProxy.loginEventRelay.accept(LoginEvent())

        } else {
            Log.d("login", "failed")
        }
    }
}