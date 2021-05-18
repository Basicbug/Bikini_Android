package com.example.bikini_android.manager.login

import com.example.bikini_android.manager.login.naver.NaverLoginManager
import com.example.bikini_android.manager.login.naver.NaverLoginManagerFactory

/**
 * @author bsgreentea
 */
abstract class LoginManagerFactory {

    abstract fun makeLoginManager(): LoginManager

    companion object {
        inline fun <reified T : LoginManager> createFactory(): LoginManagerFactory {
            return when (T::class) {
                NaverLoginManager::class -> NaverLoginManagerFactory()
                else -> throw IllegalArgumentException()
            }
        }
    }
}