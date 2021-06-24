/*
 * LoginViewModelTest.kt 2021. 6. 20
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android

import com.example.bikini_android.manager.login.LoginManagerProxy
import com.example.bikini_android.ui.login.LoginRepository
import com.example.bikini_android.ui.login.LoginViewModel
import com.example.bikini_android.ui.provider.TestSchedulerProvider
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

    val testSchedulerProvider = TestSchedulerProvider()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        viewModel = LoginViewModel(loginRepository, testSchedulerProvider, loginManagerProxy)
    }

    @Test
    fun signup_success_progress() {
        Mockito.`when`(loginRepository.sendTokenToServer(Mockito.anyString())).thenReturn(
            Single.just(LoginRepository.LoginJwtResult(""))
        )
        viewModel.sendTokenToServer(Mockito.anyString())

        Assert.assertTrue(viewModel.progressViewModel.isVisible)
        testSchedulerProvider.testScheduler.triggerActions()
        Assert.assertFalse(viewModel.progressViewModel.isVisible)
    }

    @Test
    fun signup_success_complete_event() {
        Mockito.`when`(loginRepository.sendTokenToServer(Mockito.anyString())).thenReturn(
            Single.just(LoginRepository.LoginJwtResult(""))
        )
        viewModel.sendTokenToServer(Mockito.anyString())
        var isCompleteEventInvoked = false
        viewModel.itemEventRelay
            .ofType(LoginViewModel.CompleteEvent::class.java)
            .observeOn(testSchedulerProvider.testScheduler)
            .subscribe {
                isCompleteEventInvoked = true
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
}