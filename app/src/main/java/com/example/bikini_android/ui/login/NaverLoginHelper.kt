package com.example.bikini_android.ui.login

import android.content.Intent
import android.view.View
import com.example.bikini_android.manager.NaverLoginManager
import com.example.bikini_android.ui.holder.MainHolderActivity
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton
import io.reactivex.disposables.CompositeDisposable

/**
 * @author bsgreentea
 */
class NaverLoginHelper(
    private val activity: LoginActivity,
    private val disposable: CompositeDisposable
) {

    fun startMainHolder() {
        activity.startActivity(
            Intent(activity, MainHolderActivity::class.java)
        )
        activity.finish()
    }

    fun customizePropertiesView(view: View) {
        (view as OAuthLoginButton).setOAuthLoginHandler(NaverLoginManager)
    }
}