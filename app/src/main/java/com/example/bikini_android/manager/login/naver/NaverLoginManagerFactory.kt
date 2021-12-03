package com.example.bikini_android.manager.login.naver

import com.basicbug.core.manager.login.LoginManager
import com.example.bikini_android.manager.login.LoginManagerFactory

/**
 * @author bsgreentea
 */
class NaverLoginManagerFactory : LoginManagerFactory() {

    override fun makeLoginManager(): LoginManager = NaverLoginManager.instance
}