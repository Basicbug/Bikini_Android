package com.example.bikini_android.ui.databinding

import android.view.View
import androidx.databinding.BindingAdapter
import com.example.bikini_android.ui.login.NaverLoginHelper

/**
 * @author bsgreentea
 */
object BindingAdapter {
    @JvmStatic
    @BindingAdapter("setManagerFromHelper")
    fun setManagerFromHelper(
        view: View, helper: NaverLoginHelper
    ) {
        helper.customizePropertiesView(view)
    }
}