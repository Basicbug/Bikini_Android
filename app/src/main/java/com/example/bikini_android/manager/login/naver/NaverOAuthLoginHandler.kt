package com.example.bikini_android.manager.login.naver

import android.util.Log
import com.basicbug.core.app.AppResources
import com.example.bikini_android.manager.login.LoginManagerFactory
import com.example.bikini_android.manager.login.LoginManagerProxy
import com.example.bikini_android.ui.login.LoginEvent
import com.nhn.android.naverlogin.OAuthLoginHandler

/**
 * @author bsgreentea
 */
class NaverOAuthLoginHandler : OAuthLoginHandler() {
    override fun run(success: Boolean) {
        if (success) {

            LoginManagerProxy.setLoginManager(
                LoginManagerFactory.createFactory<NaverLoginManager>()
                    .makeLoginManager()
            )
            val accessToken =
                NaverLoginManager.instance.loginInstance.getAccessToken(AppResources.getContext())
            LoginManagerProxy.loginEventRelay.accept(LoginEvent(accessToken))

        } else {
            Log.d("login", "failed")
        }
    }
}