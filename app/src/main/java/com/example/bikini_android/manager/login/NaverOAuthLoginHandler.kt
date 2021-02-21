package com.example.bikini_android.manager.login

import android.util.Log
import com.example.bikini_android.R
import com.example.bikini_android.app.AppResources
import com.example.bikini_android.manager.PreferenceManager
import com.example.bikini_android.ui.login.LoginEvent
import com.nhn.android.naverlogin.OAuthLoginHandler

/**
 * @author bsgreentea
 */
class NaverOAuthLoginHandler : OAuthLoginHandler() {
    override fun run(success: Boolean) {
        if (success) {
            PreferenceManager.setBoolean(AppResources.getStringResId(R.string.is_logged_in), true)
            PreferenceManager.setString(
                AppResources.getStringResId(R.string.last_login_platform),
                AppResources.getStringResId(R.string.naver_id_login)
            )
            LoginManagerProxy.loginEventRelay.accept(LoginEvent())
        } else {
            Log.d("login", "failed")
        }
    }
}