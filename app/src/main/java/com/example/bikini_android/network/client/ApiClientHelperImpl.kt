/*
 * ApiClientHepler.kt 2021. 12. 3
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.network.client

import com.basicbug.network.HostSelectionInterceptor
import com.basicbug.network.TokenAuthenticator
import com.basicbug.network.client.ApiClientHelper
import com.basicbug.network.client.ApiFactory
import com.example.bikini_android.manager.PreferenceManagerImpl
import com.example.bikini_android.manager.login.LoginManagerProxy
import com.example.bikini_android.network.NetworkConstants
import com.example.bikini_android.ui.settings.DeveloperSettingImpl
import com.example.bikini_android.ui.settings.FlipperSettingImpl
import retrofit2.Retrofit

/**
 * @author MyeongKi
 */
object ApiClientHelperImpl : ApiClientHelper() {
    override val mainClient: Retrofit = ApiFactory.getMainApiClient(
        NetworkConstants.DEV_URL,
        TokenAuthenticator(this, LoginManagerProxy, PreferenceManagerImpl),
        listOf(
            HostSelectionInterceptor(DeveloperSettingImpl),
            FlipperSettingImpl().getFlipperNetworkPlugin()
        )
    )
    override val invalidAuthClient: Retrofit = ApiFactory.getInvalidApiClient(
        NetworkConstants.DEV_URL,
        listOf(
            HostSelectionInterceptor(DeveloperSettingImpl),
            FlipperSettingImpl().getFlipperNetworkPlugin()
        )
    )
}