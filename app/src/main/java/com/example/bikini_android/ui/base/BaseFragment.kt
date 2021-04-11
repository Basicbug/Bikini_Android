/*
 * BaseFragment.kt 2020. 10. 31
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bikini_android.ui.holder.MainHolderActivity
import com.example.bikini_android.ui.holder.NavigationHelperImpl
import io.reactivex.disposables.CompositeDisposable

/**
 * @author MyeongKi
 */

abstract class BaseFragment : Fragment() {
    private lateinit var _disposables: CompositeDisposable
    protected val disposables
        get() = _disposables

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _disposables = CompositeDisposable()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (!_disposables.isDisposed) {
            _disposables.dispose()
        }
    }

    protected fun getNavigationHelper(): NavigationHelperImpl? {
        return if (activity is MainHolderActivity) {
            (activity as MainHolderActivity).navigationHelper
        } else {
            null
        }
    }
}