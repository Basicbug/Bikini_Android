package com.example.bikini_android.ui.databinding

import android.view.View
import androidx.databinding.BindingAdapter
import com.example.bikini_android.manager.login.NaverOAuthLoginHandler
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton

/**
 * @author bsgreentea
 */
@BindingAdapter("setNaverLoginHandler")
fun setNaverLoginHandler(
    view: View, loginHandler: NaverOAuthLoginHandler
) {
    (view as OAuthLoginButton).setOAuthLoginHandler(loginHandler)
}