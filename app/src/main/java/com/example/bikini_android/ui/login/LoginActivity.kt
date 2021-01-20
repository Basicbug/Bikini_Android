package com.example.bikini_android.ui.login

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.bikini_android.R
import com.example.bikini_android.databinding.ActivityLoginBinding
import com.example.bikini_android.ui.base.BaseActivity

/**
 * @author bsgreentea
 */
class LoginActivity : BaseActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.apply {
            loginHelper = NaverLoginHelper(this@LoginActivity, disposables)
        }
    }
}