package com.example.bikini_android.manager.login

/**
 * @author bsgreentea
 */
class NaverLoginManagerFactory : LoginManagerFactory() {

    override fun makeLoginManager(): LoginManager = NaverLoginManager.instance
}