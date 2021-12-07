package com.basicbug.core.manager.login

import com.basicbug.core.util.bus.RxAction
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