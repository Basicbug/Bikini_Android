package com.example.bikini_android.ui.databinding

import android.view.View
import androidx.databinding.BindingAdapter
import com.example.bikini_android.manager.login.NaverOAuthLoginHandler
import com.nhn.android.naverlogin.OAuthLoginHandler
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton

/**
 * @author bsgreentea
 */
@BindingAdapter("setOAuthLoginHandler")
fun setOAuthLoginHandler(
    view: View, loginHandler: OAuthLoginHandler
) {
    (view as OAuthLoginButton).setOAuthLoginHandler(loginHandler)
}