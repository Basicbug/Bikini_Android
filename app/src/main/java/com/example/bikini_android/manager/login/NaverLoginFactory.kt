package com.example.bikini_android.manager.login

/**
 * @author bsgreentea
 */
class NaverLoginFactory : LoginFactory() {

    override fun makeLoginManager(): LoginManager = NaverLoginManager()
}