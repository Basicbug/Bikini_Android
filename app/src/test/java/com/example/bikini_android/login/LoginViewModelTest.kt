/*
 * LoginViewModelTest.kt 2021. 6. 20
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.login

import com.basicbug.core.rx.TestSchedulerProvider
import com.basicbug.network.response.TokenResponse
import com.example.bikini_android.manager.login.LoginManagerProxy
import com.example.bikini_android.repository.account.AccountRepositoryImpl
import com.example.bikini_android.repository.account.UserInfo
import com.example.bikini_android.ui.login.LoginRepository
import com.example.bikini_android.ui.login.LoginViewModel
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * @author MyeongKi
 */
class LoginViewModelTest {
    private lateinit var viewModel: LoginViewModel

    @Mock
    lateinit var loginRepository: LoginRepository

    @Mock
    lateinit var loginManagerProxy: LoginManagerProxy

    @Mock
    lateinit var accountRepository: AccountRepositoryImpl

    private val testSchedulerProvider = TestSchedulerProvider()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        viewModel = LoginViewModel(loginRepository, testSchedulerProvider, loginManagerProxy, accountRepository)
    }

    @Test
    fun signup_success_progress() {
        Mockito.`when`(loginRepository.sendTokenToServer(Mockito.anyString())).thenReturn(
            Single.just(TokenResponse.Result("", ""))
        )
        viewModel.sendTokenToServer(Mockito.anyString())

        Assert.assertTrue(viewModel.progressViewModel.isVisible)
        testSchedulerProvider.testScheduler.triggerActions()
        Assert.assertFalse(viewModel.progressViewModel.isVisible)
    }

    @Test
    fun signup_success_complete_event() {
        Mockito.`when`(loginRepository.sendTokenToServer(Mockito.anyString())).thenReturn(
            Single.just(TokenResponse.Result("", ""))
        )
        viewModel.sendTokenToServer(Mockito.anyString())
        var isCompleteEventInvoked = false
        viewModel.itemEventRelay
            .ofType(LoginViewModel.EventType::class.java)
            .observeOn(testSchedulerProvider.testScheduler)
            .subscribe {
                isCompleteEventInvoked = it == LoginViewModel.EventType.COMPLETE
            }
        testSchedulerProvider.testScheduler.triggerActions()
        Assert.assertTrue(isCompleteEventInvoked)
    }

    @Test
    fun signup_fail_progress() {
        Mockito.`when`(loginRepository.sendTokenToServer(Mockito.anyString())).thenReturn(
            Single.error(Exception())
        )

        viewModel.sendTokenToServer(Mockito.anyString())

        Assert.assertTrue(viewModel.progressViewModel.isVisible)
        testSchedulerProvider.testScheduler.triggerActions()
        Assert.assertFalse(viewModel.progressViewModel.isVisible)
    }

    @Test
    fun check_my_info_success_exist() {

        Mockito.`when`(accountRepository.getMyInfo()).thenReturn(
            Single.just(UserInfo("nickname", 0, 0))
        )

        viewModel.checkMyInfo()
        var alreadyExists = false
        viewModel.itemEventRelay
            .ofType(LoginViewModel.EventType::class.java)
            .observeOn(testSchedulerProvider.testScheduler)
            .subscribe {
                alreadyExists = it == LoginViewModel.EventType.ALREADY_EXIST
            }
        testSchedulerProvider.testScheduler.triggerActions()

        Assert.assertTrue(alreadyExists)
    }

}