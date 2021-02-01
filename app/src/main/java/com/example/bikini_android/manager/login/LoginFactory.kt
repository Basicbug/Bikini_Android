package com.example.bikini_android.manager.login

/**
 * @author bsgreentea
 */
abstract class LoginFactory {

    abstract fun makeLoginManager(): LoginManager

    companion object {
        inline fun <reified T : LoginManager> createFactory(): LoginFactory {
            return when (T::class) {
                NaverLoginManager::class -> NaverLoginFactory()
                else -> throw IllegalArgumentException()
            }
        }
    }
}