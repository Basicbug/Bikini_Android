package com.example.bikini_android.manager

import android.util.Log
import com.example.bikini_android.app.AppResources
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler

/**
 * @author bsgreentea
 */
object NaverLoginManager : OAuthLoginHandler() {

    private const val CLIENT_ID = "XegnHxsuJTCKfKRJYn95"
    private const val CLIENT_SECRET = "074hXOqltm"

    private val appContext = AppResources.getContext()

    private val loginInstance = OAuthLogin.getInstance().apply {
        init(
            appContext,
            CLIENT_ID,
            CLIENT_SECRET, "Bikini"
        )
    }

    fun isAlreadyLogin(): Boolean =
        PreferenceManager.getBoolean("isAuthorized")

    override fun run(success: Boolean) {
        if (success) {
            PreferenceManager.setBoolean("isAuthorized", true)
        } else {
            Log.d("login", "failed")
        }
    }

    fun getLoginInstance(): OAuthLogin = loginInstance

    fun logout() {
        loginInstance?.logout(appContext)
    }
}