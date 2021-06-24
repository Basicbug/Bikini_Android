package com.example.bikini_android.manager.login

import com.example.bikini_android.util.bus.RxAction
import com.jakewharton.rxrelay2.Relay

/**
 * @author bsgreentea
 */
interface LoginManager {

    val loginEventRelay: Relay<RxAction>
    fun logOut()
    fun isLoggedIn(): Boolean
    fun successLogin()
}