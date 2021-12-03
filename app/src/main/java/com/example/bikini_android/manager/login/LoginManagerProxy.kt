package com.example.bikini_android.manager.login

import com.basicbug.core.app.AppResources
import com.basicbug.core.manager.login.LoginManager
import com.basicbug.core.util.bus.RxAction
import com.basicbug.network.TokenManager
import com.example.bikini_android.R
import com.example.bikini_android.manager.PreferenceManagerImpl
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay

/**
 * @author bsgreentea
 */
object LoginManagerProxy : LoginManager, TokenManager {
    override var accessToken: String = ""
        get() {
            if (field.isEmpty()) {
                field = PreferenceManagerImpl.getString(AppResources.getString(R.string.access_token))
            }
            return field
        }
        set(value) {
            value.let {
                field = it
                PreferenceManagerImpl.setString(AppResources.getString(R.string.access_token), it)
            }
        }

    override var refreshToken: String = ""
        get() {
            if (field.isEmpty()) {
                field = PreferenceManagerImpl.getString(AppResources.getString(R.string.refresh_token))
            }
            return field
        }
        set(value) {
            value.let {
                field = it
                PreferenceManagerImpl.setString(AppResources.getString(R.string.refresh_token), it)
            }
        }

    var userName: String = ""
        get() {
            if (field.isEmpty()) {
                field = PreferenceManagerImpl.getString(AppResources.getString(R.string.user_name_key))
            }
            return field
        }
        set(value) {
            value.let {
                field = it
                PreferenceManagerImpl.setString(AppResources.getString(R.string.user_name_key), it)
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
        PreferenceManagerImpl.apply {
            setBoolean(AppResources.getString(R.string.is_logged_in), false)
        }
    }

    override fun isLoggedIn() =
        loginManager?.isLoggedIn()
            ?: PreferenceManagerImpl.getBoolean(AppResources.getString(R.string.is_logged_in))

    override fun successLogin() {
        loginManager?.successLogin()
    }
}