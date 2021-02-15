package com.example.bikini_android.manager.login

import android.util.Log
import com.example.bikini_android.R
import com.example.bikini_android.app.AppResources
import com.example.bikini_android.manager.PreferenceManager
import com.example.bikini_android.ui.login.LoginEvent
import com.example.bikini_android.util.bus.RxAction
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler

/**
 * @author bsgreentea
 */
class NaverLoginManager : LoginManager, OAuthLoginHandler() {

    private val appContext = AppResources.getContext()

    override val loginEventRelay: Relay<RxAction> = PublishRelay.create()

    private val loginInstance = OAuthLogin.getInstance().apply {
        init(
            appContext,
            AppResources.getStringResId(R.string.naver_login_client_id),
            AppResources.getStringResId(R.string.naver_login_client_secret),
            "Bikini"
        )
    }

    override fun run(success: Boolean) {
        if (success) {
            PreferenceManager.setBoolean(AppResources.getStringResId(R.string.is_logged_in), true)
            PreferenceManager.setString(
                AppResources.getStringResId(R.string.last_login_platform),
                AppResources.getStringResId(R.string.naver_id_login)
            )
            loginEventRelay.accept(LoginEvent())
        } else {
            Log.d("login", "failed")
        }
    }

    override fun isLoggedIn(): Boolean {
        return PreferenceManager.getBoolean(AppResources.getStringResId(R.string.is_logged_in))
    }

    override fun logOut() {
        loginInstance?.logout(
            appContext
        )
    }

    companion object {
        val instance = NaverLoginManager()
    }
}
