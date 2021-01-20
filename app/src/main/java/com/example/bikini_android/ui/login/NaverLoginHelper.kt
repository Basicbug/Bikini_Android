package com.example.bikini_android.ui.login

import android.view.View
import com.example.bikini_android.manager.NaverLoginManager
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton

/**
 * @author bsgreentea
 */
class NaverLoginHelper {

    fun customizePropertiesView(view: View) {
        (view as OAuthLoginButton).setOAuthLoginHandler(NaverLoginManager)
    }
}