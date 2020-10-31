/*
 * MainHolderActivity.kt 2020. 10. 31
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.mainholder

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.bikini_android.R
import com.example.bikini_android.databinding.ActivityMainHolderBinding
import com.example.bikini_android.ui.base.BaseActivity

/**
 * @author MyeongKi
 */

class MainHolderActivity : BaseActivity(){
    lateinit var binding: ActivityMainHolderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_holder)

    }
}