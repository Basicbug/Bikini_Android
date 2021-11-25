/*
 * SelectImageNavBottomSheet.kt 2021. 7. 31
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.bikini_android.R
import com.example.bikini_android.databinding.ViewSelectImageMethodBottomSheetBinding
import com.basicbug.core.util.bus.RxAction
import com.basicbug.core.util.ktx.autoCleared
import com.basicbug.core.rx.addTo
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jakewharton.rxrelay2.Relay
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * @author MyeongKi
 */
class SelectImageMethodBottomSheet(private val itemEventRelay: Relay<RxAction>) :
    BottomSheetDialogFragment() {
    private var binding by autoCleared<ViewSelectImageMethodBottomSheetBinding>()
    private lateinit var viewModel: SelectImageMethodViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_Design_BikiniBottomSheet)
        isCancelable = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ViewSelectImageMethodBottomSheetBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[SelectImageMethodViewModel::class.java]
        viewModel.itemEventRelay = itemEventRelay
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemEventRelay
            .ofType(SelectImageMethodViewModel.EventType::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                dismiss()
            }.addTo(viewModel.disposables)
    }
}