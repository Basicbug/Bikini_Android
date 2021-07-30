package com.example.bikini_android.manager.login.naver

import com.example.bikini_android.R
import com.example.bikini_android.app.AppResources
import com.example.bikini_android.manager.PreferenceManager
import com.example.bikini_android.manager.login.LoginManager
import com.example.bikini_android.util.bus.RxAction
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import com.nhn.android.naverlogin.OAuthLogin

/**
 * @author bsgreentea
 */
class NaverLoginManager : LoginManager {

    private val appContext = AppResources.getContext()

    override val loginEventRelay: Relay<RxAction> = PublishRelay.create()

    val loginInstance: OAuthLogin = OAuthLogin.getInstance()

    override fun isLoggedIn(): Boolean {
        return PreferenceManager.getBoolean(AppResources.getString(R.string.is_logged_in))
    }

    override fun logOut() {
        loginInstance.logout(appContext)
    }

    override fun successLogin() {

        PreferenceManager.apply {
            setBoolean(AppResources.getString(R.string.is_logged_in), true)
            setString(
                AppResources.getString(R.string.last_login_platform),
                AppResources.getString(R.string.naver_id_login)
            )
        }
    }

    companion object {
        val instance = NaverLoginManager()
    }
}
