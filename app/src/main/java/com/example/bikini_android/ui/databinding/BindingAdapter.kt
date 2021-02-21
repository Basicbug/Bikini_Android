package com.example.bikini_android.ui.databinding

import android.view.View
import androidx.databinding.BindingAdapter
import com.example.bikini_android.manager.login.LoginManager
import com.example.bikini_android.manager.login.NaverOAuthLoginHandler
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton

/**
 * @author bsgreentea
 */
@BindingAdapter("setLoginHandler")
fun setLoginHandler(
    view: View, loginManager: LoginManager
) {
    (view as OAuthLoginButton).setOAuthLoginHandler(NaverOAuthLoginHandler())
}