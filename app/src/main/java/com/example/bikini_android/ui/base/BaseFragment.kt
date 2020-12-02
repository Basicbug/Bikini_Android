/*
 * BaseFragment.kt 2020. 10. 31
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.base

import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable

/**
 * @author MyeongKi
 */

abstract class BaseFragment : Fragment() {
    protected val disposables: CompositeDisposable = CompositeDisposable()
    override fun onDestroyView() {
        super.onDestroyView()
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }
}