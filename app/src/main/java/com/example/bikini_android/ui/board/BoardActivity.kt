package com.example.bikini_android.ui.board

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.bikini_android.R
import com.example.bikini_android.databinding.ActivityBoardBinding
import com.example.bikini_android.ui.base.BaseActivity

class BoardActivity : BaseActivity() {

    lateinit var binding: ActivityBoardBinding
    private val viewModel: BoardViewModel by lazy {
        ViewModelProvider(this)[BoardViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board)

        binding.apply {
            boardViewModel = viewModel
        }

        setUpObservers()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE) {
            if (data != null) {
                val imageUri = data.data
                Log.d("uri", imageUri.toString())
            }
        }

    }

    private fun setUpObservers() {
        viewModel.onPickImageClicked.observe(this, Observer {

            val galleryIntent = Intent(Intent.ACTION_PICK).apply {
                type = "image/*"
            }
            startActivityForResult(galleryIntent, PICK_IMAGE)
        })

        viewModel.onPublishClicked.observe(this, Observer {
            finish()
        })
    }

    companion object {
        private const val PICK_IMAGE = 1
    }
}