package com.example.bikini_android.manager.login

import com.example.bikini_android.util.bus.RxAction
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay

/**
 * @author bsgreentea
 */
object LoginManagerProxy : LoginManager {

    private var loginManager: LoginManager? = null

    override val loginEventRelay: Relay<RxAction> = PublishRelay.create()

    fun getLoginManager() = loginManager ?: NullPointerException("no manager")

    fun setLoginManager(manager: LoginManager) {
        loginManager = manager
    }

    override fun logOut() {
        if (loginManager is NaverLoginManager) {
            (loginManager as NaverLoginManager).logOut()
        }
    }

    override fun isLoggedIn(): Boolean {
        return if (loginManager is NaverLoginManager) {
            (loginManager as NaverLoginManager).isLoggedIn()
        } else false
    }
}