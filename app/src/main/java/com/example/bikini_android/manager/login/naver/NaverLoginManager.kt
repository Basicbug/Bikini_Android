package com.example.bikini_android.manager.login.naver

import com.basicbug.core.app.AppResources
import com.basicbug.core.util.bus.RxAction
import com.example.bikini_android.R
import com.example.bikini_android.manager.PreferenceManagerImpl
import com.basicbug.core.manager.login.LoginManager
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
        return PreferenceManagerImpl.getBoolean(AppResources.getString(R.string.is_logged_in))
    }

    override fun logOut() {
        loginInstance.logout(appContext)
    }

    override fun successLogin() {

        PreferenceManagerImpl.apply {
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
