package com.example.bikini_android.manager.login

import com.example.bikini_android.R
import com.example.bikini_android.app.AppResources
import com.example.bikini_android.manager.PreferenceManager
import com.example.bikini_android.util.bus.RxAction
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay

/**
 * @author bsgreentea
 */
object LoginManagerProxy : LoginManager {

    var jwt: String = ""
        get() = field
        set(value) {
            value.let {
                field = it
                PreferenceManager.setString(AppResources.getStringResId(R.string.jwt), it)
            }
        }

    private var loginManager: LoginManager? = null

    override val loginEventRelay: Relay<RxAction> = PublishRelay.create()

    fun getLoginManager() = loginManager ?: NullPointerException("there's no manager")

    fun setLoginManager(manager: LoginManager) {
        loginManager = manager
    }

    override fun logOut() {
        loginManager?.logOut()
    }

    override fun isLoggedIn() =
        loginManager?.isLoggedIn() ?: PreferenceManager.getBoolean(AppResources.getStringResId(R.string.is_logged_in))

    override fun successLogin() {
        loginManager?.successLogin()
    }
}